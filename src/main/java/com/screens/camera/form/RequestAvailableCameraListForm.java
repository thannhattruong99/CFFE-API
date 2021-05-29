package com.screens.camera.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class RequestAvailableCameraListForm {
    @Min(value = 1, message = "MSG-009") @Max(value = 2, message = "MSG-009")
    private int typeDetect;

    public RequestAvailableCameraListForm() {
    }

    public int getTypeDetect() {
        return typeDetect;
    }

    public void setTypeDetect(int typeDetect) {
        this.typeDetect = typeDetect;
    }
}
