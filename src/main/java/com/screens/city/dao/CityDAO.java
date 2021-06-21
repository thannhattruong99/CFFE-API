package com.screens.city.dao;

import com.common.dao.BaseDAO;
import com.screens.city.form.CityResponseSupporter;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityDAO extends BaseDAO {

    public CityDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public List<CityResponseSupporter> getCityDistrict(){
        try{
            openConnection();
            return this.sqlSession.selectList("CityDAO.getCityAndDistrict");
        }finally {
            closeConnection();
        }
    }
}
