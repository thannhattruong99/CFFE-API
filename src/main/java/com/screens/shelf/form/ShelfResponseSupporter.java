package com.screens.shelf.form;

public class ShelfResponseSupporter {
    private String shelfId;
    private String shelfName;
    private int numberOfStack;
    private String updatedTime;
    private String statusId;
    private String statusName;
    private String description;

    public ShelfResponseSupporter() {
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public int getNumberOfStack() {
        return numberOfStack;
    }

    public void setNumberOfStack(int numberOfStack) {
        this.numberOfStack = numberOfStack;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
