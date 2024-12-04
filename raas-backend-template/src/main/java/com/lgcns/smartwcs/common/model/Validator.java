package com.lgcns.smartwcs.common.model;

import com.lgcns.smartwcs.common.exception.InvalidRequestException;

/**
 * 검증 가능한 Validate 메소드가 있는 인터페이스
 */
public interface Validator {
    void validate() throws InvalidRequestException;
}
