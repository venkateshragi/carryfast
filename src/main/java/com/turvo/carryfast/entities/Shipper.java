package com.turvo.carryfast.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Shipper {

    private String id;
    private List<String> shipments;
    @JsonFormat(pattern = "dd/MM/yyyy, HH:mm:ss")
    private Date lastCheckedTime;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getShipments() {
        return shipments;
    }

    public void setShipments(List<String> shipments) {
        this.shipments = shipments;
    }

    public Date getLastCheckedTime() {
        return lastCheckedTime;
    }

    public void setLastCheckedTime(Date lastCheckedTime) {
        this.lastCheckedTime = lastCheckedTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
