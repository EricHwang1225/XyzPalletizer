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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lgcns.smartwcs.common.model.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <PRE>
 * 사용자 Entity 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO extends BaseDTO {

    private String coCd;
    private String cntrCd;
    private String userId;
    private String userNm;

    @JsonIgnore
    private String pwd;

    @JsonIgnore
    private String passwordConfirm;

    private Integer userLvl;
    private String roleCd;
    private String pwdInitYn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String pwdChgDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String loginDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String firstLoginDt;

    private String logoutDt;
    private Integer loginFailCnt;
    private String loginYn;
    private String kioskYn;
    private String useYn;

    public User dtoMapping() {

        return User.builder()
                .coCd(this.getCoCd())
                .cntrCd(this.getCntrCd())
                .userId(this.getUserId())
                .userNm(this.getUserNm())
                .userLvl(this.getUserLvl())
                .roleCd(this.getRoleCd())
                .pwdInitYn(this.getPwdInitYn())
                .kioskYn(this.getKioskYn())
                .useYn(this.getUseYn())
                .regId(this.getRegId())
                .updId(this.getUpdId())
                .build();
    }
}
