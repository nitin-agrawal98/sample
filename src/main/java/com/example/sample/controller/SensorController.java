package com.example.sample.controller;

import com.example.sample.dto.AvgDataDTO;
import com.example.sample.dto.AvgDataRequestDTO;
import com.example.sample.model.Sensor;
import com.example.sample.model.SensorData;
import com.example.sample.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    @Autowired
    SensorService sensorService;

    @PostMapping
    public Sensor saveSensor(@RequestBody Sensor sensor) {
        return sensorService.saveSensor(sensor);
    }

    @GetMapping("/{id}/data")
    public List<SensorData> getDataBySensor(@PathVariable("id") Long id) {
        return sensorService.getDataBySensor(id);
    }

    @PostMapping("/{id}/data")
    public SensorData saveData(@PathVariable("id") Long id, @RequestBody SensorData data) {
        return sensorService.saveData(id, data);
    }

    @PostMapping("/avg")
    public AvgDataDTO getAvgData(@RequestBody AvgDataRequestDTO requestDTO) {
        return sensorService.getAvgData(requestDTO);
    }
}
