package com.screens.manager.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RequestUpdateManagerStatusForm implements Serializable {
    @NotEmpty(message = "MSG-001") @Size(min = 1, max = 30, message = "MSG-063")
    private String userName;
    @Min(value = 2, message = "MSG-065") @Max(value = 3, message = "MSG-065")
    private int statusId;
    @Nullable @Size(min = 1, max = 250, message = "MSG-067")
    private String reasonInactive;

    public RequestUpdateManagerStatusForm() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
