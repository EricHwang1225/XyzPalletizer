/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.code.model;

import lombok.*;

/**
 * <PRE>
 * 공통 코드 검색 조건 클래스.
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
public class CodeSearchCondition {

    /**
     * 회사 코드
     */
    private String coCd;

    /**
     * 대분류 코드
     */
    private String comHdrCd;
    /**
     * 대분류 이름
     */
    private String comHdrNm;
    /**
     * 상세 분류 코드
     */
    private String comDtlCd;
    /**
     * 상세 분류 이름
     */
    private String comDtlNm;
    /**
     * 사용 여부
     */
    private String useYn;
    /**
     * 상세코드 사용 여부
     */
    private String useYnDt;
    /**
     * 대분류 플래그
     */
    private String hdrFlag;

    /**
     * Excel Download FileName
     */
    private String fileName;

    /**
     * selected 대분류 코드
     */
    private String selectedComHdrCd;

    /**
     * Language Code 대분류 코드
     */
    private String langCd;
}
