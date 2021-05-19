package com.screens.manager.dto;

import com.common.dto.BaseDTO;

public class ManagerDTO extends BaseDTO {
    private String status;

    public ManagerDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
