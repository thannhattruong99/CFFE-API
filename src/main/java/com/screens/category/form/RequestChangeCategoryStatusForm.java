package com.screens.category.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

public class RequestChangeCategoryStatusForm implements Serializable {

    private int categoryId;

    @Min(value = 1, message = "MSG-065") @Max(value = 3, message = "MSG-065")
    private int statusId;


    public RequestChangeCategoryStatusForm() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
