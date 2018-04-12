package com.turvo.carryfast.notification;

import java.util.Date;
import java.util.List;

public class Message {

    private String shipmentId;
    private List<String> destinations;
    private Date messageCreatedAt;

    public Message(String shipmentId, List<String> destinations, Date messageCreatedAt) {
        this.shipmentId = shipmentId;
        this.destinations = destinations;
        this.messageCreatedAt = messageCreatedAt;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    public Date getMessageCreatedAt() {
        return messageCreatedAt;
    }

    public void setMessageCreatedAt(Date messageCreatedAt) {
        this.messageCreatedAt = messageCreatedAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "shipmentId='" + shipmentId + '\'' +
                ", destinations=" + destinations +
                '}';
    }
}
