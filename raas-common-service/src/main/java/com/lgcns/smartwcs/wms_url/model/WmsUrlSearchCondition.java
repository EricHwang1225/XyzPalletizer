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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <PRE>
 * Tenant 검색 조건 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WmsUrlSearchCondition {

    /**
     * 선택한 회사 코드(Tenant Code)
     */
    private String searchCoCd;

    /**
     * 회사 코드(Tenant 코드)
     */
    private String coCd;

    /**
     * 회사 명(Tenant 명)
     */
    private String coNm;

    /**
     * 회사 명(Tenant Name)
     */
    private String wmsIfId;

    /**
     * 사용여부
     */
    private String wmsIfUrl;


    /**
     * page No
     */
    @Schema(description = "페이지 번호(0...N)")
    private Integer page;

    /**
     * page Size
     */
    @Schema(description = "페이지 크기")
    private Integer size;

    /**
     * Excel Download FileName
     */
    private String fileName;


}
