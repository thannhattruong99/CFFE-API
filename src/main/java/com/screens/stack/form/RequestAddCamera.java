package com.screens.stack.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestAddCamera implements Serializable {
    @NotEmpty
    private String stackId;

    @NotEmpty
    private String cameraId;

    private int action;

    public RequestAddCamera() {
    }

    public String getStackId() {
        return stackId;
    }

    public void setStackId(String stackId) {
        this.stackId = stackId;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
