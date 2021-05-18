package com.screens.manager.dto;

import com.common.dto.BaseDTO;

public class ManagerDTO extends BaseDTO {
    private String managerName;
    private String username;
    private String status;

    public ManagerDTO() {
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
