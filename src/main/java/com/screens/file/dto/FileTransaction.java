package com.screens.file.dto;

import java.util.Date;

public class FileTransaction {
    private String user;
    private Notification notification;
    private Date when;

    public FileTransaction(String user, Notification notification, Date when) {
        this.user = user;
        this.notification = notification;
        this.when = when;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }
}
