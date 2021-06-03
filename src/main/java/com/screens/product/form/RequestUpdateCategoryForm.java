package com.screens.product.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class RequestUpdateCategoryForm implements Serializable {

    @NotEmpty(message = "MSG-096")
    private String productId;

    @NotNull(message = "MSG-102")
    private List<Integer> categories;

    public RequestUpdateCategoryForm() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
}
