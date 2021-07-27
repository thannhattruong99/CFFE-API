package com.screens.file.dto;


import java.util.List;

public class FileTransaction {
    private int totalFile;
    private int numberFileDone;
    private List<String> fileSuccess;
    private List<String> fileError;

    public FileTransaction(int totalFile, int numberFileDone, List<String> fileSuccess, List<String> fileError) {
        this.totalFile = totalFile;
        this.numberFileDone = numberFileDone;
        this.fileSuccess = fileSuccess;
        this.fileError = fileError;
    }

    public int getTotalFile() {
        return totalFile;
    }

    public void setTotalFile(int totalFile) {
        this.totalFile = totalFile;
    }

    public int getNumberFileDone() {
        return numberFileDone;
    }

    public void setNumberFileDone(int numberFileDone) {
        this.numberFileDone = numberFileDone;
    }

    public List<String> getFileSuccess() {
        return fileSuccess;
    }

    public void setFileSuccess(List<String> fileSuccess) {
        this.fileSuccess = fileSuccess;
    }

    public List<String> getFileError() {
        return fileError;
    }

    public void setFileError(List<String> fileError) {
        this.fileError = fileError;
    }
}
