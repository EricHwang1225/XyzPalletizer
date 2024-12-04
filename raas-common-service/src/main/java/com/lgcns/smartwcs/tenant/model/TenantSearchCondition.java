/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.tenant.model;

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
public class TenantSearchCondition {

    /**
     * 회사 코드(Tenant Code)
     */
    private String coCd;

    /**
     * 회사 명(Tenant Name)
     */
    private String coNm;

    /**
     * 사용여부
     */
    private String useYn;

    /**
     * Excel Download FileName
     */
    private String fileName;

    /**
     * Search CoCd
     */
    private String searchCoCd;

    private String userId;

    private String userNm;

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

    private String treeType;

    private String treeId;

    private String langCd;

}
