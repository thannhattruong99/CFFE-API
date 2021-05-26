package com.screens.store.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RequestChangeStoreStatusForm implements Serializable {
    @NotEmpty @NotNull
    private String storeId;

    @Min(value = 1, message = "MSG-065") @Max(value = 3, message = "MSG-065")
    private int statusId;

    @Nullable
    private String reasonInactive;

    public RequestChangeStoreStatusForm() {
    }

    @Nullable
    public String getReasonInactive() {
        return reasonInactive;
    }

    public void setReasonInactive(@Nullable String reasonInactive) {
        this.reasonInactive = reasonInactive;
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
