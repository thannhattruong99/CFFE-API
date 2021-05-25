package com.screens.store.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RequestChangeStoreStatusForm {
    @NotEmpty @NotNull
    private String storeId;

    @Min(value = 1, message = "MSG-009") @Max(value = 3, message = "MSG-009")
    private int statusId;

    public RequestChangeStoreStatusForm() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
