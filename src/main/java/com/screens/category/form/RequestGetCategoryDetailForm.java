package com.screens.category.form;

import java.io.Serializable;

public class RequestGetCategoryDetailForm implements Serializable {
    
    private int CategoryId;

    public RequestGetCategoryDetailForm() {
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }
}
