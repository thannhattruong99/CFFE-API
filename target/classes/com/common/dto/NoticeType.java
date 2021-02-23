package com.common.dto;

public class NoticeType {
    private Integer typeid;

    private String typename;

    public NoticeType(Integer typeid, String typename) {
        this.typeid = typeid;
        this.typename = typename;
    }

    public NoticeType() {
        super();
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }
}