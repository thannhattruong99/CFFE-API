package com.screens.product.form;

import com.common.form.RequestGetBaseForm;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

public class RequestGetProductListForm extends RequestGetBaseForm implements Serializable {

    private int categoryId;

    @Min(value = 0, message = "MSG-009") @Max(value = 3, message = "MSG-009")
    private int statusId;

    public RequestGetProductListForm() {
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
