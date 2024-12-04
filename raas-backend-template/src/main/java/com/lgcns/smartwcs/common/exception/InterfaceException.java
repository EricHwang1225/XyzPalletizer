package com.lgcns.smartwcs.common.exception;

import java.util.List;

/**
 * 요청이 잘못되었을 때 생기는 exception
 */
public class InterfaceException extends Exception {
    private String trackingId;
    private List<String> messageArray;
    private String errorMessage;

    public InterfaceException(String trackingId, List<String> messageArray) {
        this.trackingId = trackingId;
        this.messageArray = messageArray;
    }

    public InterfaceException(String trackingId, String errorMessage) {
        this.trackingId = trackingId;
        this.errorMessage = errorMessage;
    }

    public String getTrackingId() {
        return this.trackingId;
    }

    public List<String> getMessageArray() {
        return this.messageArray;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
