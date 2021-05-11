package com.common.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErrorObject {
    private String code;
    private String message;

    public ErrorObject() {
    }

    public ErrorObject(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}