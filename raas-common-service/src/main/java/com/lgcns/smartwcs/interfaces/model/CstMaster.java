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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <PRE>
 * Cst Master Entity 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CstMaster {

    /**
     * tracking ID
     */
    private String trackingId;

    /**
     * 회사 코드
     */
    @Schema(name = "회사 코드")
    private String coCd;

    /**
     * 화주 코드
     */
    @Schema(name = "화주 코드")
    private String cstCd;

    /**
     * 화주 명
     */
    @Schema(name = "화주 명")
    private String cstNm;

    /**
     * 화주 주소
     */
    @Schema(name = "화주 주소")
    private String addr;

    /**
     * 연락처
     */
    @Schema(name = "연락처")
    private String telNo;

    /**
     * 사용여부
     */
    @Schema(name = "사용여부")
    private String useYn;

    /**
     * 등록자 아이디
     */
    @Schema(name = "등록자 아이디")
    private String regId;

    /**
     * 등록일시
     */
    @Schema(name = "등록일시")
    private String regDt;


}
