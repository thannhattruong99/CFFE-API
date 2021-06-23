package com.screens.shelf.dto;

import java.util.Date;

public class StockTransaction {
    String user;
    Notification notification;
    Date when;

    public StockTransaction(String user, Notification notification, Date when) {
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
