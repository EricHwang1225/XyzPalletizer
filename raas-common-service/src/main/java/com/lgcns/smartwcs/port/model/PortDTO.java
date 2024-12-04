/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.port.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lgcns.smartwcs.common.model.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <PRE>
 * 사용자 Entity 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PortDTO extends BaseDTO {

    private String coCd;
    private String cntrCd;
    private String eqpId;
    private String portId;
    private String portNm;
    private String useYn;
    private Integer sortSeq;
    private String portDesc;
    private String pcIp;
    private String lightIp;
    private String lightUseYn;
    private String wrInId;
    private String wrOutId;
    private String portStrtYn;
    private String userId;

    public Port dtoMapping() {

        return Port.builder()
                .coCd(this.getCoCd())
                .cntrCd(this.getCntrCd())
                .eqpId(this.getEqpId())
                .portId(this.getPortId())
                .portNm(this.getPortNm())
                .useYn(this.getUseYn())
                .sortSeq(this.getSortSeq())
                .portDesc(this.getPortDesc())
                .pcIp(this.getPcIp())
                .lightIp(this.getLightIp())
                .lightUseYn(this.getLightUseYn())
                .wrInId(this.getWrInId())
                .wrOutId(this.getWrOutId())
                .portStrtYn(this.getPortStrtYn())
                .userId(this.getUserId())
                .regId(this.getRegId())
                .updId(this.getUpdId())
                .build();
    }
}
