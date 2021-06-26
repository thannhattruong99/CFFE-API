package com.screens.product.form;

import java.io.Serializable;

public class ProductShortResponseSupporter implements Serializable {
    private String productId;
    private String productName;

    public ProductShortResponseSupporter() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
