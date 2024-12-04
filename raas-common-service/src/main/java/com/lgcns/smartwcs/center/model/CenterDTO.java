/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.center.model;

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
public class CenterDTO extends BaseDTO {

    private String coCd;
    private String cntrCd;
    private String cntrNm;
    private String useYn;
    private String addr;
    private String telNo;
    private String cntrEmail;

    private Integer sortSeq;

    public Center dtoMapping() {

        return Center.builder()
                .coCd(this.getCoCd())
                .cntrCd(this.getCntrCd())
                .cntrNm(this.getCntrNm())
                .useYn(this.getUseYn())
                .sortSeq(this.getSortSeq())
                .addr(this.getAddr())
                .telNo(this.getTelNo())
                .cntrEmail(this.getCntrEmail())
                .regId(this.getRegId())
                .updId(this.getUpdId())
                .build();
    }
}
