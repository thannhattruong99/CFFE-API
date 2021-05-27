package com.screens.camera.form;

import java.util.List;

public class ResponseAvailableCameraListForm {
    private int totalOfRecord;
    private List<AvailableCameraListResponseSupporter> cameras;

    public ResponseAvailableCameraListForm() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public List<AvailableCameraListResponseSupporter> getCameras() {
        return cameras;
    }

    public void setCameras(List<AvailableCameraListResponseSupporter> cameras) {
        this.cameras = cameras;
    }
}
