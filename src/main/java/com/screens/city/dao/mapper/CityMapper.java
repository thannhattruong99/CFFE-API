package com.screens.city.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.city.form.CityResponseSupporter;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityMapper extends BaseDAO {

    public CityMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public List<CityResponseSupporter> getCityDistrict(){
        return this.sqlSession.selectList("CityDAO.getCityAndDistrict");
    }
}
