package com.common.form;

import java.util.List;

public class ResponseCommonForm {
    private List<String> errorCodes;

    public ResponseCommonForm() {
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}