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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <PRE>
 * 로그인 처리 및 토큰 발행용 모델
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    private String userNm;
    private String pwd;
    private String prePwd;
    private String userId;
    private String roleCd;
    private String coCd;
    private String cntrCd;
    private String cntrNm;
    private String eqpId;
    private String eqpNm;
    private String eqpTypeCd;
    private String jwTokenString;
    private Integer failedUpdateCnt; // 카운트가 0보다 크면 실패 카운트 업데이트도 처리함.
    private String portId;
    private List<RoleMenuResponse> roleMenuList;
    private String successYn;
    private String failedMessage;
    private String pwdInitYn;
    private Integer userLvl;
    private String tenantIcon;
    private String tenantColor;
    private String localIp;
    private String langCd;
    private String kioskYn;
    private String toteRegex;
    private Integer toteUnavblMin;
    private String appNm;

}
