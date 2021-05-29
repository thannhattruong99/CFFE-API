package com.screens.manager.form;

import java.io.Serializable;

public class ManagerResponseSupporter implements Serializable {
    private String managerName;
    private String userId;
    private String userName;
    private String storeId;
    private String updatedTime;
    private String status;
    private String imageURL;


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ManagerResponseSupporter() {

    }
}
