package com.screens.stack.form;

import java.io.Serializable;
import java.util.List;

public class ResponseStackListForm implements Serializable {
    private int totalOfRecord;
    private List<ResponseStackDetailForm> stacks;

    public ResponseStackListForm() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public List<ResponseStackDetailForm> getStacks() {
        return stacks;
    }

    public void setStacks(List<ResponseStackDetailForm> stacks) {
        this.stacks = stacks;
    }
}
