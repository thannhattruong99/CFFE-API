package com.screens.city.form;

import java.io.Serializable;
import java.util.List;

public class CityResponseSupporter implements Serializable {
    private int cityId;
    private String cityName;
    private List<DistrictResponseSupporter> districts;

    public CityResponseSupporter() {
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<DistrictResponseSupporter> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictResponseSupporter> districts) {
        this.districts = districts;
    }
}
