package com.screens.category.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestUpdateInfoCategoryForm implements Serializable {
    private int CategoryId;
    @NotEmpty(message = "MSG-105")
    private String categoryName;

    public RequestUpdateInfoCategoryForm() {
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
