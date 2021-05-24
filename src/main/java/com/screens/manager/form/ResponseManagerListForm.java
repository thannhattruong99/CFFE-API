package com.screens.manager.form;

import java.io.Serializable;
import java.util.List;

public class ResponseManagerListForm implements Serializable {
    private int totalOfRecord;
    private List<ManagerResponseSupporter> managers;

    public ResponseManagerListForm() {
    }

    public List<ManagerResponseSupporter> getManagers() {
        return managers;
    }

    public void setManagers(List<ManagerResponseSupporter> managers) {
        this.managers = managers;
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }
}
