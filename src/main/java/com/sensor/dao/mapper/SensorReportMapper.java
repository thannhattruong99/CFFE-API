package com.sensor.dao.mapper;

import com.common.config.BaseDAO;
import com.screenname.dto.Account;
import com.sensor.dto.SensorReportDTO;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SensorReportMapper extends BaseDAO {
    public SensorReportMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public boolean createSensorReport(SensorReportDTO sensorReportDTO){
        System.out.println("Contain: " + sensorReportDTO.getContain());
        int result = sqlSession.insert("com.sensor.dao.sql.SensorReportDAO.insert", sensorReportDTO);
        sqlSession.commit();
        if(result >= 1){
            return true;
        }
        return false;
    }
}
