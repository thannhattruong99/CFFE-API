package com.screens.shelf.form;

import java.util.List;

public class ResponseShelfDetailForm {
    private String shelfId;
    private String shelfName;
    private String description;
    private int numberOfStack;
    private String createdTime;
    private String updatedTime;
    private String reasonInactive;
    private String statusId;
    private String statusName;
    private String cameraId;
    private String cameraName;
    private String imageURL;
    private String rtspString;
    private String cameraCreatedTime;
    private String cameraUpdatedTime;
    private List<ShelfDetailResponseSupporter> stacks;


    public ResponseShelfDetailForm() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfStack() {
        return numberOfStack;
    }

    public void setNumberOfStack(int numberOfStack) {
        this.numberOfStack = numberOfStack;
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

    public String getReasonInactive() {
        return reasonInactive;
    }

    public void setReasonInactive(String reasonInactive) {
        this.reasonInactive = reasonInactive;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRtspString() {
        return rtspString;
    }

    public void setRtspString(String rtspString) {
        this.rtspString = rtspString;
    }

    public String getCameraCreatedTime() {
        return cameraCreatedTime;
    }

    public void setCameraCreatedTime(String cameraCreatedTime) {
        this.cameraCreatedTime = cameraCreatedTime;
    }

    public String getCameraUpdatedTime() {
        return cameraUpdatedTime;
    }

    public void setCameraUpdatedTime(String cameraUpdatedTime) {
        this.cameraUpdatedTime = cameraUpdatedTime;
    }

    public List<ShelfDetailResponseSupporter> getStacks() {
        return stacks;
    }

    public void setStacks(List<ShelfDetailResponseSupporter> stacks) {
        this.stacks = stacks;
    }
}
