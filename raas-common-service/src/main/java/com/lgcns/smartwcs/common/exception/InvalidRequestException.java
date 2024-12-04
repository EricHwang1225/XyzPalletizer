package com.lgcns.smartwcs.common.exception;

import java.util.Map;

/**
 * 요청이 잘못되었을 때 생기는 exception
 */
public class InvalidRequestException extends Exception {
    private final Map<String, String> fieldMessage;

    public InvalidRequestException(Map<String, String> fieldMessage) {
        this.fieldMessage = fieldMessage;
    }

    public Map<String, String> getFieldMessage() {
        return fieldMessage;
    }
}
