package com.screens.store.form;

import com.common.form.RequestGetBaseForm;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

public class RequestGetStoreListForm extends RequestGetBaseForm implements Serializable {
    @Min(value = 0, message = "MSG-009") @Max(value = 3, message = "MSG-009")
    private int statusId;

    public RequestGetStoreListForm() {
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
