package com.screens.camera.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class RequestAvailableCameraListForm {
    private String cameraName;
    private int pageNum;
    private int fetchNext;
    @Min(value = 1, message = "MSG-009") @Max(value = 2, message = "MSG-009")
    private int typeDetect;


    public RequestAvailableCameraListForm() {
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getFetchNext() {
        return fetchNext;
    }

    public void setFetchNext(int fetchNext) {
        this.fetchNext = fetchNext;
    }

    public int getTypeDetect() {
        return typeDetect;
    }

    public void setTypeDetect(int typeDetect) {
        this.typeDetect = typeDetect;
    }
}
