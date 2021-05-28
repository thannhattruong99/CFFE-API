package com.screens.stack.form;

import java.io.Serializable;

public class ResponseStackDetailForm implements Serializable {
    private String stackId;
    private int position;
    private String createTime;
    private String updatedTime;
    private String reasonInactive;
    private String shelfId;
    private String shelfName;
    private CameraResponseSupporter camera;
    private ProductResponseSupporter product;
    private int statusId;
    private String statusName;

    public ResponseStackDetailForm() {
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStackId() {
        return stackId;
    }

    public void setStackId(String stackId) {
        this.stackId = stackId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getReasonInactive() {
        return reasonInactive;
    }

    public void setReasonInactive(String reasonInactive) {
        this.reasonInactive = reasonInactive;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public CameraResponseSupporter getCamera() {
        return camera;
    }

    public void setCamera(CameraResponseSupporter camera) {
        this.camera = camera;
    }

    public ProductResponseSupporter getProduct() {
        return product;
    }

    public void setProduct(ProductResponseSupporter product) {
        this.product = product;
    }
}
