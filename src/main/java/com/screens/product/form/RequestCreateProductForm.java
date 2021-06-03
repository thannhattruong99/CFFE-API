package com.screens.product.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

public class RequestCreateProductForm implements Serializable {
    @NotEmpty
    private String ProductName;
    @Nullable
    private String imageUrl;
    @Nullable
    private String description;
    @NotEmpty
    private List<Integer> categories;

    public RequestCreateProductForm() {
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
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

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
}
