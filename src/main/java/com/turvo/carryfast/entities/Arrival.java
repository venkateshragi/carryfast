package com.turvo.carryfast.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Arrival {

    @JsonFormat(pattern = "dd/MM/yyyy, HH:mm:ss")
    private Date timeStamp;
    private List<String> expectedShipments;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<String> getExpectedShipments() {
        return expectedShipments;
    }

    public void setExpectedShipments(List<String> expectedShipments) {
        this.expectedShipments = expectedShipments;
    }
}
