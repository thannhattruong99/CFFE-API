package com.screens.manager.form;

import com.common.form.ResponseGetBaseForm;

import java.io.Serializable;
import java.util.List;

public class ResponseManagerListForm extends ResponseGetBaseForm implements Serializable {
    private List<ManagerResponseSupporter> managers;

    public ResponseManagerListForm() {
    }

    public List<ManagerResponseSupporter> getManagers() {
        return managers;
    }

    public void setManagers(List<ManagerResponseSupporter> managers) {
        this.managers = managers;
    }
}
