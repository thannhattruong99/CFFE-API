package com.screens.stack.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RequestAddProduct implements Serializable {
    @NotEmpty
    private String stackId;

    @NotEmpty
    private String productId;

    private int action;

    public RequestAddProduct() {
    }

    public String getStackId() {
        return stackId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setStackId(String stackId) {
        this.stackId = stackId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
