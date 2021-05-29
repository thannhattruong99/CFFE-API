package com.screens.shelf.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class RequestChangeShelfCameraForm {
    @NotEmpty(message = "MSG-071")
    private String shelfId;
    @NotEmpty(message = "MSG-020")
    private String cameraId;
    @Min(value = 1, message = "MSG-076") @Max(value = 2, message = "MSG-076")
    private int action;

    public RequestChangeShelfCameraForm() {
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
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
