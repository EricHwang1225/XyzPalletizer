package com.lgcns.smartwcs.common.exception;

public class CommonRuntimeException extends RuntimeException {
    private String errorMessage;

    public CommonRuntimeException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
