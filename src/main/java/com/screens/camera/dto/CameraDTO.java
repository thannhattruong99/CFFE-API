package com.screens.camera.dto;

import com.common.dto.BaseDTO;

public class CameraDTO extends BaseDTO {
    private int typeDetect;

    public CameraDTO() {
    }

    public int getTypeDetect() {
        return typeDetect;
    }

    public void setTypeDetect(int typeDetect) {
        this.typeDetect = typeDetect;
    }
}
