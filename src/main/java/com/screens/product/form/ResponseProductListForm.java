package com.screens.product.form;

import java.io.Serializable;
import java.util.List;

public class ResponseProductListForm implements Serializable {
    private int totalOfRecord;
    private List<ResponseProductDetailForm> products;

    public ResponseProductListForm() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public List<ResponseProductDetailForm> getProducts() {
        return products;
    }

    public void setProducts(List<ResponseProductDetailForm> products) {
        this.products = products;
    }
}
