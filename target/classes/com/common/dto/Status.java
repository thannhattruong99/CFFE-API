package com.common.dto;

public class Status {
    private Integer statusid;

    private String statusname;

    public Status(Integer statusid, String statusname) {
        this.statusid = statusid;
        this.statusname = statusname;
    }

    public Status() {
        super();
    }

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname == null ? null : statusname.trim();
    }
}