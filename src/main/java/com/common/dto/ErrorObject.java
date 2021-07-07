package com.common.dto;


import java.util.HashMap;
import java.util.Map;

public class ErrorObject {
    private Map<String, String> errorCodeAndMsg;

    public ErrorObject() {
        errorCodeAndMsg = new HashMap<>();
    }

    public Map<String, String> getErrorCodeAndMsg() {
        return errorCodeAndMsg;
    }

    public void setErrorCodeAndMsg(Map<String, String> errorCodeAndMsg) {
        this.errorCodeAndMsg = errorCodeAndMsg;
    }
}