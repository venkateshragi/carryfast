package com.turvo.carryfast.notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.turvo.carryfast.entities.Shipment;

public class PeriodicEmitterImpl implements Emitter, Runnable {

    private int timeToWait;
    private MessageChannel messageChannel;
    private Shipment shipment;
    private Map<String, Date> destinationArrivalMap;

    public PeriodicEmitterImpl(int millis, Shipment shipment, MessageChannel messageChannel, Map<String, Date> destinationArrivalMap) {
        this.timeToWait = millis;
        this.messageChannel = messageChannel;
        this.shipment = shipment;
        this.destinationArrivalMap = destinationArrivalMap;
    }

    @Override
    public void sendMessage() {
        Date currentTimeStamp = new Date();
        Message message = new Message(shipment.getId(), shipment.getNextDestinations(), currentTimeStamp);
        messageChannel.addMessage(message);
        shipment.setLastSeenTime(currentTimeStamp);
        List<String> destinationIds = new ArrayList<>();
        for (Map.Entry<String, Date> entry : destinationArrivalMap.entrySet()) {
            if (currentTimeStamp.compareTo(entry.getValue()) > 0)
                destinationIds.add(entry.getKey());
        }
        shipment.getNextDestinations().removeAll(destinationIds);
        destinationIds.forEach(e -> destinationArrivalMap.remove(e));
    }

    @Override
    public void run() {
        while (true) {
            sendMessage();
            try {
                wait(timeToWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
