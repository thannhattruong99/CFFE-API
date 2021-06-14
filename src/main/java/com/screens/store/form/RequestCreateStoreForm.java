package com.screens.store.form;

import net.bytebuddy.implementation.bind.annotation.Empty;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RequestCreateStoreForm implements Serializable {
    @NotEmpty(message = "MSG-106")
    private String storeName;

    @Nullable
    private String imageUrl;

    @NotEmpty(message = "MSG-107")
    private String address;

    @NotNull
    private int districtId;

    public RequestCreateStoreForm() {
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
}
