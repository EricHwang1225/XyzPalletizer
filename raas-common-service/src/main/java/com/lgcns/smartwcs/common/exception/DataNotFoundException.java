/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.exception;

import java.util.Map;

/**
 * <PRE>
 * 데이터를 찾을 수 없을때 사용하는 Exception 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
public class DataNotFoundException extends Exception {

    private Map<String, String> fieldMessage;

    public DataNotFoundException(Map<String, String> fieldMessage) {
        this.fieldMessage = fieldMessage;
    }

    public Map<String, String> getFieldMessage() {
        return fieldMessage;
    }

    /**
     * 메시지를 포함하는 생성자
     *
     * @param message
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
