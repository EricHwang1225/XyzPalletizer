/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.login.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgcns.smartwcs.login.model.ids.LoginAccessId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * <PRE>
 * Login Access Entity 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_COM_ACCESS_LOG")
@IdClass(LoginAccessId.class)
public class LoginAccess {

    /**
     * 접속로그이력관리용 UID(WCS)
     */
    @Id
    @Column(name = "ACCESS_UID_KEY")
    @Schema(name = "접속로그이력관리용 UID(WCS)")
    private String accessUidKey;


    /* 회사 코드*/
    @Column(name = "CO_CD")
    @Schema(name = "회사 코드")
    private String coCd;
    /* 센터 코드*/
    @Column(name = "CNTR_CD")
    @Schema(name = "센터 코드")
    private String cntrCd;
    /* 사용자 ID */
    @Column(name = "USER_ID")
    @Schema(name = "사용자 ID")
    private String userId;
    /* 사용자 이름 */
    @Column(name = "USER_NM")
    @Schema(name = "사용자 이름")
    private String userNm;
    /* 접속로그유형코드(LOGIN,LOGOUT,MENU) */
    @Column(name = "ACCESS_TYPE_CD")
    @Schema(name = "접속로그유형코드")
    private String accessTypeCd;
    /* 접속로그생성일자 */
    @Column(name = "ACCESS_EXEC_DT")
    @Schema(name = "접속로그생성일자")
    private String accessExecDt;
    /* 접속PC IP정보 */
    @Column(name = "ACCESS_EXEC_IP")
    @Schema(name = "접속PC IP정보")
    private String accessExecIp;
    /* 접속PC 상세정보 */
    @Column(name = "ACCESS_EXEC_INFO")
    @Schema(name = "접속PC 상세정보")
    private String accessExecInfo;

    /* 등록자 ID */
    @Column(name = "REG_ID")
    @Schema(name = "등록자 ID")
    private String regId;

    /**
     * 등록일시
     */
    @Schema(name = "등록일시")
    @Column(name = "REG_DT")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDt;
}
