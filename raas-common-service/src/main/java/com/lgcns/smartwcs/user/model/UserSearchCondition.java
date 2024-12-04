/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <PRE>
 * 사용자 검색 조건 클래스.
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
public class UserSearchCondition {

    /**
     * 회사 코드
     */
    private String coCd;

    /**
     * 검색 회사 코드
     */
    private String searchCoCd;

    /**
     * 회사 이름
     */
    private String coNm;

    /**
     * 센터코드
     */
    private String cntrCd;

    /**
     * 센터 이름
     */
    private String cntrNm;

    /**
     * 검색 센터 코드
     */
    private String searchCntrCd;

    /**
     * 사용자 아이디
     */
    private String userId;

    /**
     * 사용자 이름
     */
    private String userNm;

    /**
     * 사용자 레벨
     */
    private Integer userLvl;

    /**
     * 사용여부
     */
    private String useYn;

    /**
     * Excel Download FileName
     */
    private String fileName;

    /**
     * 권한 명
     */
    private String roleNm;

    /**
     * 권한 명
     */
    private String kioskYn;

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


}
