package com.screens.store.form;

import java.io.Serializable;

public class RequestCreateStoreForm implements Serializable {
    private String storeName;
    private String imageUrl;
    private String address;
    private String analyzedTime;
    private int districtId;

    public RequestCreateStoreForm() {
    }

    public String getAnalyzedTime() {
        return analyzedTime;
    }

    public void setAnalyzedTime(String analyzedTime) {
        this.analyzedTime = analyzedTime;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
}
