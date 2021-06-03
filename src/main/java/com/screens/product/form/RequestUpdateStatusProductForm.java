package com.screens.product.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestUpdateStatusProductForm implements Serializable {
    @NotEmpty(message = "MSG-096")
    private String productId;

    @Min(value = 1, message = "MSG-065") @Max(value = 3, message = "MSG-065")
    private int statusId;

    @Nullable
    private String reasonInactive;

    public RequestUpdateStatusProductForm() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Nullable
    public String getReasonInactive() {
        return reasonInactive;
    }

    public void setReasonInactive(@Nullable String reasonInactive) {
        this.reasonInactive = reasonInactive;
    }
}
