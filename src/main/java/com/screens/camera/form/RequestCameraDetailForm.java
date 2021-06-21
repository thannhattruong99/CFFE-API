package com.screens.camera.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;

public class RequestCameraDetailForm {
//    @Nullable
//    private String storeId;
    @NotEmpty(message = "MSG-024")
    private String cameraId;

    public RequestCameraDetailForm() {
    }

//    @Nullable
//    public String getStoreId() {
//        return storeId;
//    }
//
//    public void setStoreId(@Nullable String storeId) {
//        this.storeId = storeId;
//    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }
}
