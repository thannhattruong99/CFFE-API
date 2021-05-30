package com.screens.camera.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class RequestUpdateCameraStatusForm {
    @NotEmpty(message = "MSG-024")
    private String cameraId;
    @Min(value = 2, message = "MSG-065") @Max(value = 3, message = "MSG-065")
    private int statusId;
    @Nullable
    private String reasonInactive;

    public RequestUpdateCameraStatusForm() {
    }

    @Nullable
    public String getReasonInactive() {
        return reasonInactive;
    }

    public void setReasonInactive(@Nullable String reasonInactive) {
        this.reasonInactive = reasonInactive;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
