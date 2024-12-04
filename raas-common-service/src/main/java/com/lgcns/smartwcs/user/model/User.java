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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgcns.smartwcs.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_COM_USER_MST", indexes = @Index(name = "USER__IDX", columnList = "USER_ID, CO_CD, CNTR_CD"))
public class User extends BaseEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "CO_CD")
    private String coCd;

    @Column(name = "CNTR_CD")
    private String cntrCd;

    @Column(name = "USER_NM")
    private String userNm;

    @Column(name = "PWD")
    @JsonIgnore
    private String pwd;

    @Column(name = "SALT", columnDefinition = "sha-256 encrypt시 사용될 salt key")
    private String salt;

    @Transient
    @JsonIgnore
    private String passwordConfirm;

    @Column(name = "USER_LVL")
    private Integer userLvl;

    @Column(name = "ROLE_CD")
    private String roleCd;

    @Column(name = "PWD_INIT_YN")
    private String pwdInitYn;

    @Column(name = "USER_EQP_ID_MAP")
    private String userEqpIdMap;

    @Column(name = "PWD_CHG_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime pwdChgDt;

    @Column(name = "LOGIN_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime loginDt;

    @Column(name = "FIRST_LOGIN_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime firstLoginDt;

    @Column(name = "LOGOUT_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime logoutDt;

    @Column(name = "LOGIN_FAIL_CNT")
    @ColumnDefault("0")
    private Integer loginFailCnt;

    @Column(name = "LOGIN_YN")
    @ColumnDefault("'N'")
    private String loginYn;

    @Column(name = "KIOSK_YN")
    @ColumnDefault("'N'")
    private String kioskYn;

    @Column(name = "USE_YN")
    @ColumnDefault("'Y'")
    private String useYn;
}
