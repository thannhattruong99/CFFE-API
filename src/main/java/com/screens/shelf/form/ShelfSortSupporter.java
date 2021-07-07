package com.screens.shelf.form;

import java.io.Serializable;

public class ShelfSortSupporter implements Serializable {
    private String shelfId;
    private String shelfName;

    public ShelfSortSupporter() {
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
}
