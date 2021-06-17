package com.screens.city.service;

import com.screens.city.dao.CityDAO;
import com.screens.city.form.CityDistrictResponseSupporter;
import com.screens.city.form.CityResponseSupporter;
import com.screens.city.form.DistrictResponseSupporter;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService {

    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    @Autowired
    private CityDAO cityDAO;

    public List<CityDistrictResponseSupporter> getCityDistrict(){
        List<CityDistrictResponseSupporter> responseForm = new ArrayList<>();
        try {
            List<CityResponseSupporter> resultDAO = cityDAO.getCityDistrict();
            if(resultDAO.size() <= 0){
                return null;
            }
            convertResultDAOToResponseResult(resultDAO, responseForm);
        }catch (PersistenceException e){
            logger.error("Error at CityService: " + e.getMessage());
        }
        return responseForm;
    }

    public void convertResultDAOToResponseResult(List<CityResponseSupporter> resultDAO, List<CityDistrictResponseSupporter> responseForm){
        Map<String, String> districts;
        for (CityResponseSupporter city: resultDAO) {
            CityDistrictResponseSupporter cityDistrictSupporter = new CityDistrictResponseSupporter();
            cityDistrictSupporter.setCityId(String.valueOf(city.getCityId()));
            cityDistrictSupporter.setCityName(city.getCityName());
            districts = new HashMap<String, String>();
            for (DistrictResponseSupporter districtSupporter: city.getDistricts()) {
                districts.put(String.valueOf(districtSupporter.getDistrictId()), districtSupporter.getDistrictName());
            }
            cityDistrictSupporter.setDistricts(districts);

            responseForm.add(cityDistrictSupporter);
        }
    }
}
