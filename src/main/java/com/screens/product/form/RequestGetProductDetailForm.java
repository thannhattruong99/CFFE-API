package com.screens.product.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestGetProductDetailForm implements Serializable {
    @NotEmpty(message = "MSG-096")
    private String productId;

    public RequestGetProductDetailForm() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
