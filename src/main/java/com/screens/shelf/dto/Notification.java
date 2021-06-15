package com.screens.shelf.dto;

public class Notification {
    private String requestId;
    private int statusId;

    public Notification() {
    }

    public Notification(String requestId, int statusId) {
        this.requestId = requestId;
        this.statusId = statusId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
