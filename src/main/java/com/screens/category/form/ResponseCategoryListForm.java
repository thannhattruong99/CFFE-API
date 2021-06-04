package com.screens.category.form;

import java.io.Serializable;
import java.util.List;

public class ResponseCategoryListForm implements Serializable {
    private int totalOfRecord;
    private List<ResponseCategoryDetailForm> categories;

    public ResponseCategoryListForm() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public List<ResponseCategoryDetailForm> getCategories() {
        return categories;
    }

    public void setCategories(List<ResponseCategoryDetailForm> categories) {
        this.categories = categories;
    }
}
