package com.screens.shelf.form;

import java.util.List;

public class ResponseShelfListForm {
    private int totalOfRecord;
    private List<ShelfResponseSupporter> shelves;

    public ResponseShelfListForm() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public List<ShelfResponseSupporter> getShelves() {
        return shelves;
    }

    public void setShelves(List<ShelfResponseSupporter> shelves) {
        this.shelves = shelves;
    }
}
