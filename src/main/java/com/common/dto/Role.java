package com.common.dto;

public class Role {
    private Integer roleid;

    private String rolename;

    public Role(Integer roleid, String rolename) {
        this.roleid = roleid;
        this.rolename = rolename;
    }

    public Role() {
        super();
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }
}