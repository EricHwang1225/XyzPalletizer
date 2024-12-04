/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.wms_url.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.BaseDTO;
import com.lgcns.smartwcs.common.model.Validator;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;

/**
 * <PRE>
 * Tenant Entity 클래스.
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
public class WmsUrlDTO extends BaseDTO {

    private String coCd;
    private String wmsIfId;
    private String wmsIfUrl;
    private String wmsIfNm;

    public WmsUrl dtoMapping() {

        return WmsUrl.builder()
                .coCd(this.getCoCd())
                .wmsIfId(this.getWmsIfId())
                .wmsIfUrl(this.getWmsIfUrl())
                .wmsIfNm(this.getWmsIfNm())
                .regId(this.getRegId())
                .updId(this.getUpdId())
                .build();
    }
}
