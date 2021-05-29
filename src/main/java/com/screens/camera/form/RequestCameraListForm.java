package com.screens.camera.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RequestCameraListForm {
    @Nullable @Size(min = 1, max = 36, message = "MSG-035")
    private String storeId;
    @Nullable
    private String cameraName;
    @Min(value = 0, message = "MSG-065") @Max(value = 3, message = "MSG-065")
    private int statusId;

    public RequestCameraListForm() {
    }

    @Nullable
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(@Nullable String storeId) {
        this.storeId = storeId;
    }

    @Nullable
    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(@Nullable String cameraName) {
        this.cameraName = cameraName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
