package com.example.sample.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "avgData")
public class AvgDataDTO {
    private Double avgTemperature;
    private Double avgHumidity;

    public Double getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(Double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public Double getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(Double avgHumidity) {
        this.avgHumidity = avgHumidity;
    }
}
