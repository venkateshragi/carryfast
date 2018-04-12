package com.turvo.carryfast.entities;

import java.util.List;

public class Destination {

    private String id;
    private List<Arrival> arrivals;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Arrival> getArrivals() {
        return arrivals;
    }

    public void setArrivals(List<Arrival> arrivals) {
        this.arrivals = arrivals;
    }
}
