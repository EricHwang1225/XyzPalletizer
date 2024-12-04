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
public class AutoStoreResponse {

    @JacksonXmlProperty(localName = "params")
    private Params params;

    @JacksonXmlProperty(localName = "fault")
    private Fault fault;

    public class Params {
        @JacksonXmlProperty(localName = "port_id")
        private String portId;

        public String getPortId() {
            return portId;
        }

        @JacksonXmlProperty(localName = "bin_id")
        private String binId;

        public String getBinId() {
            return binId;
        }

        @JacksonXmlProperty(localName = "task_id")
        private String taskId;

        public String getTaskId() {
            return taskId;
        }

        @JacksonXmlProperty(localName = "bins")
        private Bins bins;

        public Bins getBins() {
            return bins;
        }

        public class Bins {

            @JacksonXmlProperty(localName = "bin")
            private Bin bin;

            public Bin getBin() {
                return bin;
            }
            public class Bin {
                @JacksonXmlProperty(localName = "bin_id")
                private String binId;

                @JacksonXmlProperty(localName = "content")
                private String content;

                @JacksonXmlProperty(localName = "target_grid")
                private String targetGrid;

                @JacksonXmlProperty(localName = "xpos")
                private String xpos;

                @JacksonXmlProperty(localName = "ypos")
                private String ypos;

                @JacksonXmlProperty(localName = "dept")
                private String dept;

                @JacksonXmlProperty(localName = "port_id")
                private String portId;

                @JacksonXmlProperty(localName = "bin_mode")
                private String binMode;

                public String getBinMode() {
                    return binMode;
                }
                public String getPortId() {
                    return portId;
                }
            }
        }
    }

    public class Fault {

        @JacksonXmlProperty(localName = "code")
        private String code;

        public String getCode() {
            return code;
        }
    }
}
