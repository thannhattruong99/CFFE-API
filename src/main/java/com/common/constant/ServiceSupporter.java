package com.common.constant;

public enum ServiceSupporter {
    ALL_STATUS(0), ACTIVE(1), PENDING(2);

    private int value;

    ServiceSupporter(int i) {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
