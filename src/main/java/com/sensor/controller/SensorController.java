package com.sensor.controller;

import com.sensor.dto.SensorReportDTO;
import com.sensor.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SensorController {
    @Autowired
    SensorService sensorService;

    @RequestMapping(value = "/createSensorReport", method = RequestMethod.POST)
    @ResponseBody
    public String createSensorReport(@RequestBody SensorReportDTO sensorReportDTO){

        System.out.println(5/0);
        if(sensorService.createReport(sensorReportDTO)){
            return "Insert dc roi";
        }
        return "Chua insert dc";
    }
}
