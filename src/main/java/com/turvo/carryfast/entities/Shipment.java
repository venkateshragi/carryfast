package com.turvo.carryfast.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Shipment {

    private String id;
    private String lastSeenLocation;
    private List<String> nextDestinations;
    @JsonFormat(pattern = "dd/MM/yyyy, HH:mm:ss")
    private Date lastSeenTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastSeenLocation() {
        return lastSeenLocation;
    }

    public void setLastSeenLocation(String lastSeenLocation) {
        this.lastSeenLocation = lastSeenLocation;
    }

    public List<String> getNextDestinations() {
        return nextDestinations;
    }

    public void setNextDestinations(List<String> nextDestinations) {
        this.nextDestinations = nextDestinations;
    }

    public Date getLastSeenTime() {
        return lastSeenTime;
    }

    public void setLastSeenTime(Date lastSeenTime) {
        this.lastSeenTime = lastSeenTime;
    }
}
