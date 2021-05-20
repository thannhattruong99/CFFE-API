package com.screens.city.form;

import java.io.Serializable;

public class DistrictResponseSupporter implements Serializable {
    private int districtId;
    private String districtName;

    public DistrictResponseSupporter() {
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}