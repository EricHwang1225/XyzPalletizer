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

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * Inbd Master Entity 클래스
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
public class TenantDetail {

    /**
     * Tree ID
     */
    @Schema(name = "Tree ID")
    private String treeId;

    /**
     * Tree Type
     */
    @Schema(name = "Tree Type")
    private String treeType;

    /**
     * 회사 코드
     */
    @Schema(name = "회사 코드")
    private String coCd;

    /**
     * 회사 코드
     */
    @Schema(name = "회사 코드")
    private String coNm;

    /**
     * Tenant Icon
     */
    @Schema(name = "Tenant Icon")
    private String tenantIcon;

    /**
     * 계약 시작 일자
     */
    @Schema(name = "계약 시작 일자")
    private String contrStrtYmd;

    /**
     * 계약 종료 일자
     */
    @Schema(name = "계약 종료 일자")
    private String contrEndYmd;

    /**
     * Background Color
     */
    @Schema(name = "Background Color")
    private String bkgdColor;

    /**
     * 센터 코드
     */
    @Schema(name = "센터 코드")
    private String cntrCd;

    /**
     * 센터 명
     */
    @Schema(name = "센터 명")
    private String cntrNm;

    /**
     * 설비 코드
     */
    @Schema(name = "설비 코드")
    private String eqpId;

    /**
     * 설비 명
     */
    @Schema(name = "설비 명")
    private String eqpNm;

    /**
     * Application 명
     */
    @Schema(name = "Application 명")
    private String appNm;

    /**
     * 오토스토어 IP
     */
    @Schema(name = "오토스토어 IP")
    private String ifServIp;

    /**
     * 설비 설명
     */
    @Schema(name = "설비 설명")
    private String eqpDesc;

    /**
     * 설비 타입
     */
    @Schema(name = "설비 타입")
    private String eqpTypeCd;
    /**
     * 재위치
     */
    @Schema(name = "위치")
    private String locCd;

    /**
     * 포트 ID
     */
    @Schema(name = "포트 ID")
    private String portId;

    /**
     * 포트 명
     */
    @Schema(name = "포트 명")
    private String portNm;

    /**
     * 포트 설명
     */
    @Schema(name = "포트 설명")
    private String portDesc;

    /**
     * 포트 PC IP
     */
    @Schema(name = "포트 PC IP")
    private String pcIp;

    /**
     * Light IP
     */
    @Schema(name = "Light IP")
    private String lightIp;

    /**
     * Light 사용 유무
     */
    @Schema(name = "Light 사용 유무")
    private String lightUseYn;

    /**
     * 정렬 순서
     */
    @Schema(name = "정렬 순서")
    private Integer sortSeq;

    /**
     * 사용 유무
     */
    @Schema(name = "사용 유무")
    private String useYn;

    /**
     * 주소
     */
    @Schema(name = "주소")
    private String addr;

    /**
     * 연락처
     */
    @Schema(name = "연락처")
    private String telNo;

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

    private String tenantEmail;

    private String cntrEmail;
}
