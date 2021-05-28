package com.screens.shelf.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RequestUpdateShelfStatusForm {
    @NotEmpty(message = "MSG-071")
    private String shelfId;
    @Min(value = 2, message = "MSG-065") @Max(value = 3, message = "MSG-065")
    private int statusId;
    @Nullable
    @Size(min = 1, max = 250, message = "MSG-067")
    private String reasonInactive;

    public RequestUpdateShelfStatusForm() {
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

    @Nullable
    public String getReasonInactive() {
        return reasonInactive;
    }

    public void setReasonInactive(@Nullable String reasonInactive) {
        this.reasonInactive = reasonInactive;
    }
}
