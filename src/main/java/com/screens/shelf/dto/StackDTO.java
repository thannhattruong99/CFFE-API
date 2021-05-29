package com.screens.shelf.dto;

public class StackDTO {
    private int position;
    private String createdTime;
    private String shelfId;
    private int statusId;

    public StackDTO(int position, String createdTime, String shelfId, int statusId) {
        this.position = position;
        this.createdTime = createdTime;
        this.shelfId = shelfId;
        this.statusId = statusId;
    }

    public String getShelfId() {
        return shelfId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
