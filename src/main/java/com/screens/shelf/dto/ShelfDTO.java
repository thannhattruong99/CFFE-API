package com.screens.shelf.dto;

import com.common.dto.BaseDTO;

public class ShelfDTO extends BaseDTO {
    private String userName;
    private String storeId;
    private String shelfId;
    private String shelfName;
    private int statusId;
    private String statusName;
    private int totalOfRecord;

    public ShelfDTO() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
