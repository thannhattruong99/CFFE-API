package com.screens.store.form;

import com.common.form.RequestGetBaseForm;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestGetStoreListByProductForm extends RequestGetBaseForm implements Serializable {
    @Min(value = 0, message = "MSG-009") @Max(value = 3, message = "MSG-009")
    private int statusId;

    @NotEmpty(message = "MSG-096")
    private String productId;

    public RequestGetStoreListByProductForm() {
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
