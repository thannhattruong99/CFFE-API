package com.sensor.dao.mapper;

import com.common.dao.BaseDAO;
import com.sensor.dto.SensorReportDTO;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class SensorReportMapper extends BaseDAO {
    public SensorReportMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public boolean createSensorReport(SensorReportDTO sensorReportDTO){
        int result = sqlSession.insert("com.sensor.dao.sql.SensorReportDAO.insert", sensorReportDTO);
        sqlSession.commit();
        if(result >= 1){
            return true;
        }
        return false;
    }
}
