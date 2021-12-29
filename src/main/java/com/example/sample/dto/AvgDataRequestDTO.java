package com.example.sample.dto;

import java.util.Set;

public class AvgDataRequestDTO {
    private Set<Long> sensorIds;
    private Long noOfDays;

    public Set<Long> getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(Set<Long> sensorIds) {
        this.sensorIds = sensorIds;
    }

    public Long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Long noOfDays) {
        this.noOfDays = noOfDays;
    }
}
