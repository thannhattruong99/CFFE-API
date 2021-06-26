package com.screens.shelf.form;

import java.io.Serializable;
import java.util.List;

public class ResponseShelvesByStoreId implements Serializable {
    private int totalOfRecord;
    private List<ShelfSortSupporter> shelves;

    public ResponseShelvesByStoreId() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public List<ShelfSortSupporter> getShelves() {
        return shelves;
    }

    public void setShelves(List<ShelfSortSupporter> shelves) {
        this.shelves = shelves;
    }
}
