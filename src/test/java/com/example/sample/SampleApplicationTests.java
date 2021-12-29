package com.example.sample;

import com.example.sample.dto.AvgDataDTO;
import com.example.sample.dto.AvgDataRequestDTO;
import com.example.sample.model.Sensor;
import com.example.sample.model.SensorData;
import com.example.sample.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SampleApplicationTests {

    @Autowired
    SensorService sensorService;

    private Sensor sensor1;
    private Sensor sensor2;
    private SensorData sensorData11;
    private SensorData sensorData12;
    private SensorData sensorData21;
    private SensorData sensorData22;


    @BeforeEach
    public void setUp() {
        sensor1 = new Sensor(1L, "Sensor1", "India", "Hyderabad");
        sensor2 = new Sensor(2L, "Sensor2", "Japan", "Tokyo");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -4);
        sensorData11 = new SensorData(1L, 24, 0.70, sensor1, cal.getTime());
        sensorData22 = new SensorData(4L, 27, 0.85, sensor2, cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 3);
        sensorData12 = new SensorData(2L, 25, 0.75, sensor1, cal.getTime());
        sensorData21 = new SensorData(3L, 26, 0.80, sensor2, cal.getTime());
        sensorService.saveSensor(sensor1);
        sensorService.saveSensor(sensor2);
        sensorService.saveData(sensor1.getId(), sensorData11);
        sensorService.saveData(sensor1.getId(), sensorData12);
        sensorService.saveData(sensor2.getId(), sensorData21);
        sensorService.saveData(sensor2.getId(), sensorData22);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(sensorService).isNotNull();
    }

    @Test
    public void getSensorByIdTest() {
        Sensor actualSensor = sensorService.getSensorById(sensor2.getId());
        assertThat(actualSensor.getId()).isEqualTo(2L);
        assertThat(actualSensor.getName()).isEqualTo("Sensor2");
        assertThat(actualSensor.getCountry()).isEqualTo("Japan");
        assertThat(actualSensor.getCity()).isEqualTo("Tokyo");
    }

    @Test
    public void getAvgWithNoDataTest() {
        AvgDataRequestDTO requestDTO = new AvgDataRequestDTO();
        Double actualAvgTemperature = sensorService.getAvgData(requestDTO).getAvgTemperature();
        Double actualAvgHumidity = sensorService.getAvgData(requestDTO).getAvgHumidity();
        assertThat(actualAvgTemperature).isEqualTo(25.5);
        assertThat(actualAvgHumidity).isEqualTo(0.775);
    }

    @Test
    public void getAvgWithSensorIdsTest() {
        AvgDataRequestDTO requestDTO = new AvgDataRequestDTO();
        requestDTO.setSensorIds(new HashSet<>(Arrays.asList(2L)));
        AvgDataDTO actualAvg = sensorService.getAvgData(requestDTO);
        assertThat(actualAvg.getAvgTemperature()).isEqualTo(26.5);
        assertThat(actualAvg.getAvgHumidity()).isEqualTo(0.825);
    }

    @Test
    public void getAvgWithSensorIdsAndPrevDaysTest() {
        AvgDataRequestDTO requestDTO = new AvgDataRequestDTO();
        requestDTO.setSensorIds(new HashSet<>(Arrays.asList(1L, 2L)));
        requestDTO.setNoOfDays(2L);
        AvgDataDTO actualAvg = sensorService.getAvgData(requestDTO);
        assertThat(actualAvg.getAvgTemperature()).isEqualTo(25.5);
        assertThat(actualAvg.getAvgHumidity()).isEqualTo(0.775);
    }
}
