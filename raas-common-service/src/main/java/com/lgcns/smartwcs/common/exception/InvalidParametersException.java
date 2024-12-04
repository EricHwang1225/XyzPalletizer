package com.lgcns.smartwcs.common.exception;

import java.util.List;

public class InvalidParametersException extends Exception {
    private List<String> messageArray;

    public List<String> getMessageArray() {
        return this.messageArray;
    }

    public InvalidParametersException(List<String> messageArray) {
        this.messageArray = messageArray;
    }
}
