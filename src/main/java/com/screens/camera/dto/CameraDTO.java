package com.screens.camera.dto;

import com.common.dto.BaseDTO;

public class CameraDTO extends BaseDTO {
    private String storeId;
    private String cameraId;
    private String cameraName;
    private int statusId;
    private int typeDetect;

    public CameraDTO() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getTypeDetect() {
        return typeDetect;
    }

    public void setTypeDetect(int typeDetect) {
        this.typeDetect = typeDetect;
    }
}
