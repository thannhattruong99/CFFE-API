package com.screens.manager.form;

import com.common.form.RequestGetBaseForm;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

public class RequestManagerListForm extends RequestGetBaseForm implements Serializable {
    @Min(value = 0, message = "MSG-009") @Max(value = 2, message = "MSG-009")
    private int status;

    public RequestManagerListForm() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
