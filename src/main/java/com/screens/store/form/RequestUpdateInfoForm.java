package com.screens.store.form;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RequestUpdateInfoForm implements Serializable {
    @NotEmpty(message = "MSG-069")
    private String storeId;

    @Nullable
    @Size(min = 1, max = 100, message = "MSG-015")
    private String storeName;

    //TODO: Image
//    @Nullable
//    private MultipartFile imageUrl;

    @Nullable
    @Size(min = 1, max = 250, message = "MSG-037")
    private String address;

    @Nullable
    private int districtId;

    @Nullable
    @Pattern(regexp = "([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]:([0-5][0-9]|[6][0])", message = "MSG-072")
    private String analyzedTime;

    public RequestUpdateInfoForm() {
    }

    @Nullable
    public String getAnalyzedTime() {
        return analyzedTime;
    }

    public void setAnalyzedTime(@Nullable String analyzedTime) {
        this.analyzedTime = analyzedTime;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Nullable
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(@Nullable String storeName) {
        this.storeName = storeName;
    }

//    @Nullable
//    public MultipartFile getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(@Nullable MultipartFile imageUrl) {
//        this.imageUrl = imageUrl;
//    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
}
