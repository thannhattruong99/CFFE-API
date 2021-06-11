package com.screens.stack.dto;

import com.common.form.RequestGetBaseForm;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestGetStackListByProductForm extends RequestGetBaseForm implements Serializable {
    @NotEmpty(message = "MSG-096")
    private String productId;

    @NotEmpty(message = "MSG-069")
    private String storeId;

    @Min(value = 0, message = "MSG-009") @Max(value = 3, message = "MSG-009")
    private int statusId;

    public RequestGetStackListByProductForm() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
