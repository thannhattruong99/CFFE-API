package com.screens.stack.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestUpdateStatusForm implements Serializable {
    @NotEmpty(message = "MSG-078")
    private String stackId;

    @Min(value = 1, message = "MSG-065") @Max(value = 3, message = "MSG-065")
    private int statusId;

    @Nullable
    private String reasonInactive;

    public RequestUpdateStatusForm() {
    }

    public String getStackId() {
        return stackId;
    }

    public void setStackId(String stackId) {
        this.stackId = stackId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Nullable
    public String getReasonInactive() {
        return reasonInactive;
    }

    public void setReasonInactive(@Nullable String reasonInactive) {
        this.reasonInactive = reasonInactive;
    }
}
