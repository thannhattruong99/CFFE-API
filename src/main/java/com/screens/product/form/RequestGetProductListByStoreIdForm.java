package com.screens.product.form;

import com.common.form.RequestGetBaseForm;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestGetProductListByStoreIdForm extends RequestGetBaseForm implements Serializable {

    @NotEmpty(message = "MSG-069")
    private String storeId;

    public RequestGetProductListByStoreIdForm() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
