package com.screens.store.form;

import net.bytebuddy.implementation.bind.annotation.Empty;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RequestCreateStoreForm implements Serializable {
    @NotEmpty
    private String storeName;

    @Nullable
    private MultipartFile imageUrl;

    @NotEmpty
    private String address;

    @NotEmpty
    private String analyzedTime;

    @NotNull
    private int districtId;

    public RequestCreateStoreForm() {
    }

    public String getAnalyzedTime() {
        return analyzedTime;
    }

    public void setAnalyzedTime(String analyzedTime) {
        this.analyzedTime = analyzedTime;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
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
