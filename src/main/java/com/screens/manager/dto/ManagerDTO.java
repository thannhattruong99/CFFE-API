package com.screens.manager.dto;

import com.common.dto.BaseDTO;

import java.sql.Date;

public class ManagerDTO extends BaseDTO {
    private String employeeCode;
    private String managerName;
    private String status;

    public ManagerDTO() {
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
