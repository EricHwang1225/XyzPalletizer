package com.lgcns.smartwcs.common.exception;

public class CommonException extends Exception {
    private String errorMessage;

    public CommonException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
