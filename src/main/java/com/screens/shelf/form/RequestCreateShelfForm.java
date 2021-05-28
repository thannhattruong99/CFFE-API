package com.screens.shelf.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RequestCreateShelfForm {
    @NotEmpty(message = "MSG-069")
    private String storeId;
    @NotEmpty(message = "MSG-079") @Size(min = 1, max = 100, message = "MSG-080")
    private String shelfName;
    @NotEmpty(message = "MSG-017")
    private String description;
    @Min(value = 1, message = "MSG-019") @Max(value = 30, message = "MSG-019")
    private int numberOfStack;

    public RequestCreateShelfForm() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfStack() {
        return numberOfStack;
    }

    public void setNumberOfStack(int numberOfStack) {
        this.numberOfStack = numberOfStack;
    }
}
