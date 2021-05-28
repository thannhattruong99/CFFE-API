package com.screens.manager.form;

import java.util.List;

public class ResponseCreateManagerForm {
    private boolean isError;
    private List<String> errorCodes;
    private String userName;

    public ResponseCreateManagerForm() {
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
