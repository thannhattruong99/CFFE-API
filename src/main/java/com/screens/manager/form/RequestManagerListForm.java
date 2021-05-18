package com.screens.manager.form;

import com.common.form.RequestGetBaseForm;

public class RequestManagerListForm extends RequestGetBaseForm {
    private String managerName;
    private String userName;
    private int status;

    public RequestManagerListForm() {
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
