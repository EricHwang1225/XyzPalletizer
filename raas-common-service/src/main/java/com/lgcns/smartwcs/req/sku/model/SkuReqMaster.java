/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.req.sku.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lgcns.smartwcs.common.model.BaseInterfaceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <PRE>
 * Sku Master Entity 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SkuReqMaster extends BaseInterfaceDTO {
    private String coCd;
    private String cstCd;
    private String skuCd;
    private String skuNm;
    private String imageUrl;
    private String skuStackMethod;
    private String useYn;

    private List<SkuReqBcdMaster> detail;

}
