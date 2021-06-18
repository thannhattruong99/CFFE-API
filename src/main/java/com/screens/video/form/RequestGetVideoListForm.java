package com.screens.video.form;

import com.common.form.RequestGetBaseForm;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class RequestGetVideoListForm extends RequestGetBaseForm {
    @Nullable
    @Pattern(regexp = "^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])",message = "MSG-117")
    private String dayStart;
    @Nullable
    @Pattern(regexp = "^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])",message = "MSG-117")
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
