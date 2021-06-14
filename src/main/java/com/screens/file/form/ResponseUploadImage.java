package com.screens.file.form;

import java.util.List;

public class ResponseUploadImage {
    private String filePath;
    private List<String> errorCodes;

    public ResponseUploadImage() {
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
