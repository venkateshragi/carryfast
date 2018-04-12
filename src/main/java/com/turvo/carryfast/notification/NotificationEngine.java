package com.turvo.carryfast.notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.turvo.carryfast.entities.Arrival;
import com.turvo.carryfast.entities.Destination;
import com.turvo.carryfast.entities.Shipment;
import com.turvo.carryfast.entities.Shipper;

@Component
public class NotificationEngine {

    private List<Shipment> shipments;
    private List<Shipper> shippers;
    private List<Destination> destinations;

    @Autowired
    private MessageChannel messageChannel;

    @Autowired
    private Notifier notifier;

    @Value("${time.to.wait.between.emits}")
    private int periodicTime;

    private Map<String, List<Shipper>> shipmentToShipperMap = new HashMap<>();
    private Map<String, List<Destination>> shipmentToDestinationMap = new HashMap<>();

    public void addShipment(Shipment shipment) {
        if (this.shipments == null)
            this.shipments = new ArrayList<>();
        this.shipments.add(shipment);
    }

    public void addDestination(Destination destination) {
        if (this.destinations == null)
            this.destinations = new ArrayList<>();
        this.destinations.add(destination);
        destination.getArrivals().forEach(e -> {
            e.getExpectedShipments().forEach(f -> {
                List<Destination> destinationList = shipmentToDestinationMap.get(f);
                if (destinationList == null) {
                    destinationList = new ArrayList<>();
                    shipmentToDestinationMap.put(f, destinationList);
                }
                destinationList.add(destination);
            });
        });
    }

    public void addShipper(Shipper shipper) {
        if (this.shippers == null)
            this.shippers = new ArrayList<>();
        this.shippers.add(shipper);
        for (String shipment : shipper.getShipments()) {
            List<Shipper> shippersList = shipmentToShipperMap.get(shipment);
            if(shippersList == null) {
                shippersList = new ArrayList<>();
                shipmentToShipperMap.put(shipment, shippersList);
            }
            shippersList.add(shipper);
        }
    }

    public void initialize() {
        for (Shipment shipment : shipments) {
            Map<String, Date> destinationArrivalMap = new HashMap<>();
            shipmentToDestinationMap.get(shipment.getId()).forEach(e -> {
                e.getArrivals().forEach(f -> {
                    if(f.getExpectedShipments().contains(shipment.getId()))
                        destinationArrivalMap.put(e.getId(), f.getTimeStamp());
                });
            });
            PeriodicEmitterImpl target = new PeriodicEmitterImpl(periodicTime, shipment, messageChannel, destinationArrivalMap);
            Thread t = new Thread(target);
            t.start();
        }
        Thread notifierThread = createNotifierThread();
    }

    private Thread createNotifierThread() {
        Thread notifierThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Message message = messageChannel.pollMessage();
                    if (message == null) {
                        try {
                            wait(periodicTime / 10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    List<Destination> destinations = shipmentToDestinationMap.get(message.getShipmentId());
                    for(Destination destination : destinations) {
                        Date timeOfArrivalOfShipmentAtThisDestination = null;
                        for(Arrival arrival : destination.getArrivals()) {
                            if(arrival.getExpectedShipments().contains(message.getShipmentId()))
                                timeOfArrivalOfShipmentAtThisDestination = arrival.getTimeStamp();
                        }
                        long min = (timeOfArrivalOfShipmentAtThisDestination.getTime() - message.getMessageCreatedAt().getTime()) / (1000l * 60);
                        if(min < 60) {
                            List<Shipper> shippers = shipmentToShipperMap.get(message.getShipmentId());
                            shippers.forEach(e -> notifier.sendMail(e, message));
                        }
                    }
                }
            }
        });
        return notifierThread;
    }

}
