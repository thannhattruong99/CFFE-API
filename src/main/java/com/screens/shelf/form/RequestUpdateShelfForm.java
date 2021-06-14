package com.screens.shelf.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RequestUpdateShelfForm {
    @NotEmpty(message = "MSG-071")
    private String shelfId;
    @NotEmpty(message = "MSG-079")
    @Size(min = 1, max = 100, message = "MSG-080")
    private String shelfName;
    @NotEmpty(message = "MSG-016")
    @Size(min = 1, max = 250, message = "MSG-017")
    private String description;
    @Nullable
    private String imageUrl;

    public RequestUpdateShelfForm() {
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
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
}
