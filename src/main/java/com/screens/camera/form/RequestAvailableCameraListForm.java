package com.screens.camera.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class RequestAvailableCameraListForm {
    @Min(value = 1, message = "MSG-009") @Max(value = 2, message = "MSG-009")
    private int typeDetect;
    private int pageNum;
    private int fetchNext;

    public RequestAvailableCameraListForm() {
    }

    public int getTypeDetect() {
        return typeDetect;
    }

    public void setTypeDetect(int typeDetect) {
        this.typeDetect = typeDetect;
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
}
