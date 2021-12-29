package com.example.sample.service;

import com.example.sample.dto.AvgDataDTO;
import com.example.sample.dto.AvgDataRequestDTO;
import com.example.sample.model.Sensor;
import com.example.sample.model.SensorData;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SensorService {
    private static final String TEMPERATURE = "temperature";
    private static final String HUMIDITY = "humidity";
    @PersistenceContext
    private EntityManager entityManager;

    public List<SensorData> getDataBySensor(Long sensorId) {
        return entityManager.createQuery("from SensorData where sensor.id=:sensorId", SensorData.class)
                .setParameter("sensorId", sensorId)
                .getResultList();
    }

    public Sensor getSensorById(Long id) {
        Sensor sensor = null;
        try {
            sensor = entityManager.find(Sensor.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensor;
    }

    @Transactional
    public SensorData saveData(Long sensorId, SensorData data) {
        Sensor sensor = getSensorById(sensorId);
        if (sensor == null) return null;
        data.setSensor(sensor);
        return entityManager.merge(data);
    }

    @Transactional
    public SensorData deleteData(SensorData data) {
        entityManager.remove(data);
        return data;
    }

    @Transactional
    public Sensor saveSensor(Sensor sensor) {
        return entityManager.merge(sensor);
    }

    @Transactional
    public Sensor deleteSensor(Sensor sensor) {
        entityManager.remove(sensor);
        return sensor;
    }

    public AvgDataDTO getAvgData(AvgDataRequestDTO requestDTO) {
        AvgDataDTO avgDataDTO = new AvgDataDTO();
        Calendar cal = Calendar.getInstance();

        Date prevDate;
        if (requestDTO.getNoOfDays() != null && requestDTO.getNoOfDays() <= 30) {
            cal.add(Calendar.DAY_OF_MONTH, -requestDTO.getNoOfDays().intValue());
        } else {
            cal.add(Calendar.MONTH, -1);
        }
        prevDate = cal.getTime();
        String queryString = "select avg(temperature), avg(humidity) from SensorData" +
                " where recordDate between :prevDate and :today";
        if (requestDTO.getSensorIds() != null && !requestDTO.getSensorIds().isEmpty()) {
            queryString += " and sensor.id in (:ids)";
        }
        Query query = entityManager.createQuery(queryString)
                .setParameter("prevDate", prevDate)
                .setParameter("today", new Date());
        if (requestDTO.getSensorIds() != null && !requestDTO.getSensorIds().isEmpty()) {
            query.setParameter("ids", requestDTO.getSensorIds());
        }
        Object[] avgMetrics = (Object[]) query.getSingleResult();
        Double avgTemperature = (Double) avgMetrics[0];
        Double avgHumidity = (Double) avgMetrics[1];
        avgDataDTO.setAvgTemperature(avgTemperature);
        avgDataDTO.setAvgHumidity(avgHumidity);
        return avgDataDTO;
    }
}
