package com.sensor.service;

import com.sensor.dao.mapper.SensorReportMapper;
import com.sensor.dto.SensorReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {
    @Autowired
    SensorReportMapper sensorReportMapper;

    public boolean createReport(SensorReportDTO sensorReportDTO){
        return sensorReportMapper.createSensorReport(sensorReportDTO);
    }
}
