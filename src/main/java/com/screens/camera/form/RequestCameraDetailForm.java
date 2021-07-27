package com.screens.camera.form;

import javax.validation.constraints.NotEmpty;

public class RequestCameraDetailForm {

    @NotEmpty(message = "MSG-024")
    private String cameraId;

    public RequestCameraDetailForm() {
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }
}
