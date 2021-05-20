package com.screens.city.form;

import java.io.Serializable;
import java.util.Map;

public class CityDistrictResponseSupporter implements Serializable {
    private String cityId;
    private String cityName;
    private Map<String, String> districts;

    public CityDistrictResponseSupporter() {
    }

    public CityDistrictResponseSupporter(String cityId, String cityName, Map<String, String> districts) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.districts = districts;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Map<String, String> getDistricts() {
        return districts;
    }

    public void setDistricts(Map<String, String> districts) {
        this.districts = districts;
    }
}