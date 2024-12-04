/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.interfaces.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.lgcns.smartwcs.common.model.BaseInterfaceDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * <PRE>
 * Sku Barcode Master Entity 클래스
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
@JsonRootName(value = "detail")
public class SkuBcdMaster extends BaseInterfaceDTO {
    @Schema(name = "회사 코드")
    private String coCd;

    @Schema(example = "화주 코드")
    private String cstCd;

    @Schema(example = "상품코드")
    private String skuCd;

    @Schema(example = "상품바코드")
    private String bcd;

    @Schema(example = "바코드 종류")
    private String bcdType;

    @Schema(example = "사용여부")
    private String useYn;
}
