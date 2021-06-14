package com.screens.stack.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestGetStackListForm implements Serializable {

    @NotEmpty(message = "MSG-071")
    private String shelfId;

    @Min(value = 0, message = "MSG-009") @Max(value = 3, message = "MSG-009")
    private int statusId;


    public RequestGetStackListForm() {
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
