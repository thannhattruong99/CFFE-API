package com.screens.product.form;

import java.io.Serializable;

public class CategoryResponseSupporter implements Serializable {
    private int categoryId;
    private String categoryName;

    public CategoryResponseSupporter() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
