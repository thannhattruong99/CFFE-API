package com.screens.stack.form;

import com.screens.product.form.CategoryResonseSupporter;

import java.io.Serializable;
import java.util.List;

public class ProductResponseSupporter implements Serializable {
    private String productId;
    private String productName;
    private String imageUrl;
    private String description;
    private String createTime;
    private String updateTime;
    private String reasonInactive;
    private int statusId;
    private String statusName;
    private List<CategoryResonseSupporter> categories;

    public ProductResponseSupporter() {
    }

    public List<CategoryResonseSupporter> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResonseSupporter> categories) {
        this.categories = categories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getReasonInactive() {
        return reasonInactive;
    }

    public void setReasonInactive(String reasonInactive) {
        this.reasonInactive = reasonInactive;
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
}
