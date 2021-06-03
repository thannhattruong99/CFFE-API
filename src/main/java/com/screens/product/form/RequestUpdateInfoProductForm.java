package com.screens.product.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestUpdateInfoProductForm implements Serializable {
    @NotEmpty(message = "MSG-096")
    private String productId;

    @Nullable
    private String productName;

    @Nullable
    private String imageUrl;

    @Nullable
    private String description;

    public RequestUpdateInfoProductForm() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Nullable
    public String getProductName() {
        return productName;
    }

    public void setProductName(@Nullable String productName) {
        this.productName = productName;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}
