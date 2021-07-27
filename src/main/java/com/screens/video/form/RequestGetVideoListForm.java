package com.screens.video.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class RequestGetVideoListForm {
    @Nullable
    private String dayStart;
    @Nullable
    private String dayEnd;
    @Min(value = 1, message = "MSG-009") @Max(value = 2, message = "MSG-009")
    private int videoType;
    @NotEmpty(message = "MSG-069")
    private String storeId;
    @Nullable
    private String shelfId;
    @Nullable
    private String productId;


    public RequestGetVideoListForm() {
    }

    @Nullable
    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(@Nullable String dayStart) {
        this.dayStart = dayStart;
    }

    @Nullable
    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(@Nullable String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    @Nullable
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(@Nullable String storeId) {
        this.storeId = storeId;
    }

    @Nullable
    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(@Nullable String shelfId) {
        this.shelfId = shelfId;
    }

    @Nullable
    public String getProductId() {
        return productId;
    }

    public void setProductId(@Nullable String productId) {
        this.productId = productId;
    }
}
