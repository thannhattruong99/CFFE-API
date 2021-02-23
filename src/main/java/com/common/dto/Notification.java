package com.common.dto;

import java.util.Date;

public class Notification {
    private Integer notificationid;

    private Integer articleid;

    private String email;

    private Date notificationdate;

    private Integer type;

    private Integer status;

    public Notification(Integer notificationid, Integer articleid, String email, Date notificationdate, Integer type, Integer status) {
        this.notificationid = notificationid;
        this.articleid = articleid;
        this.email = email;
        this.notificationdate = notificationdate;
        this.type = type;
        this.status = status;
    }

    public Notification() {
        super();
    }

    public Integer getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(Integer notificationid) {
        this.notificationid = notificationid;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getNotificationdate() {
        return notificationdate;
    }

    public void setNotificationdate(Date notificationdate) {
        this.notificationdate = notificationdate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}