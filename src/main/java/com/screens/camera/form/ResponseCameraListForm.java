package com.screens.camera.form;

import java.util.List;

public class ResponseCameraListForm {
    private int totalOfRecord;
    private List<CameraListResponseSupporter> cameras;

    public ResponseCameraListForm() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public List<CameraListResponseSupporter> getCameras() {
        return cameras;
    }

    public void setCameras(List<CameraListResponseSupporter> cameras) {
        this.cameras = cameras;
    }
}
