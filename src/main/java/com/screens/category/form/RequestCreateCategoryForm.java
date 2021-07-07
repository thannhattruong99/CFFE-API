package com.screens.category.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestCreateCategoryForm implements Serializable {
    @NotEmpty(message = "MSG-105")
    private String categoryName;

    public RequestCreateCategoryForm() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
