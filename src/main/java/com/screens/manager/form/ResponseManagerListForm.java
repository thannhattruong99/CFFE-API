package com.screens.manager.form;

import com.common.form.ResponseGetBaseForm;

import java.util.List;

public class ResponseManagerListForm extends ResponseGetBaseForm {
    private List<ManagerResponseSupporter> managers;

    public ResponseManagerListForm(List<ManagerResponseSupporter> managers) {
        this.managers = managers;
    }

    public List<ManagerResponseSupporter> getManagers() {
        return managers;
    }

    public void setManagers(List<ManagerResponseSupporter> managers) {
        this.managers = managers;
    }
}
