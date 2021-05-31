package com.screens.shelf.dto;

import com.common.dto.BaseDTO;

import java.util.List;

public class ShelfDTO extends BaseDTO {
    private String userName;
    private String storeId;
    private String shelfId;
    private String cameraId;
    private String shelfName;
    private String description;
    private int statusId;
    private int action;
    private String statusName;
    private String createdTime;
    private String updatedTime;
    private String reasonInactive;
    private int numberOfStack;
    private int totalOfRecord;
    private List<StackDTO> stacks;

    public ShelfDTO() {
    }



    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public int getNumberOfStack() {
        return numberOfStack;
    }

    public void setNumberOfStack(int numberOfStack) {
        this.numberOfStack = numberOfStack;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<StackDTO> getStacks() {
        return stacks;
    }

    public void setStacks(List<StackDTO> stacks) {
        this.stacks = stacks;
    }

    public String getReasonInactive() {
        return reasonInactive;
    }

    public void setReasonInactive(String reasonInactive) {
        this.reasonInactive = reasonInactive;
    }
}
