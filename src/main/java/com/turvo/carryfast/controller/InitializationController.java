package com.turvo.carryfast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.turvo.carryfast.entities.Destination;
import com.turvo.carryfast.entities.Shipment;
import com.turvo.carryfast.entities.Shipper;
import com.turvo.carryfast.notification.NotificationEngine;

@RestController
@RequestMapping("/turvo")
public class InitializationController {

    @Autowired
    private NotificationEngine notificationEngine;

    @RequestMapping(value = "/addShipment", method = RequestMethod.POST)
    public void addShipment(@RequestBody Shipment shipment) {
        notificationEngine.addShipment(shipment);
    }

    @RequestMapping(value = "/addDestination", method = RequestMethod.POST)
    public void addShipment(@RequestBody Destination destination) {
        notificationEngine.addDestination(destination);
    }

    @RequestMapping(value = "/addShipper", method = RequestMethod.POST)
    public void addShipment(@RequestBody Shipper shipper) {
        notificationEngine.addShipper(shipper);
    }

    @RequestMapping(value = "/initialize", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void initializeEngine() {
        notificationEngine.initialize();
    }
}
