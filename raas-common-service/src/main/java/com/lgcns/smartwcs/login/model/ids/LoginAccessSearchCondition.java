/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.login.model.ids;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <PRE>
 * Login Access 검색 조건 클래스.
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
public class LoginAccessSearchCondition {

    /**
     * 회사코드
     */
    private String coCd;

    /**
     * 센터코드
     */
    private String cntrCd;

    /**
     * 사용자 아이디
     */
    private String userId;

    /**
     * 사용자 이름
     */
    private String userNm;

    /**
     * 검색 시작 날짜 (String)
     */
    private String startDate;

    /**
     * 검색 종료 날짜 (String)
     */
    private String endDate;

    /**
     * 검색 시작 날짜
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime searchStartDate;

    /**
     * 검색 종료 날짜
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime searchEndDate;

    /**
     * 사용자 IP
     */
    private String accessExecIp;

    /**
     * 접속유형코드
     */
    private String accessTypeCd;

    /**
     * Excel Download FileName
     */
    private String fileName;

}
