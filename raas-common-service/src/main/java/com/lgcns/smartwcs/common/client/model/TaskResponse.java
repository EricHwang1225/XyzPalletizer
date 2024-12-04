/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.client.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * <PRE>
 * AutoStore Response 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@JacksonXmlRootElement(localName = "response")
public class TaskResponse {

    @JacksonXmlProperty(localName = "params")
    private Params params;

    @JacksonXmlProperty(localName = "fault")
    private Fault fault;

    public class Params {}

    public class Fault {

        @JacksonXmlProperty(localName = "code")
        private String code;

        public String getCode() {
            return code;
        }
    }
}
