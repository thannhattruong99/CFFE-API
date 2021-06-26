package com.screens.product.form;

import java.io.Serializable;
import java.util.List;

public class ResponseAllProductForm implements Serializable {
    private int totalOfRecord;
    private List<ProductShortResponseSupporter> products;

    public ResponseAllProductForm() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public List<ProductShortResponseSupporter> getProducts() {
        return products;
    }

    public void setProducts(List<ProductShortResponseSupporter> products) {
        this.products = products;
    }
}
