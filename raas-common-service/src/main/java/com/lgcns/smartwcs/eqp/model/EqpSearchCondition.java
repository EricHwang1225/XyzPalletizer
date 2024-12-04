/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.eqp.model;

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
public class EqpSearchCondition {

    /**
     * 회사코드
     */
    private String coCd;

    /**
     * 센터코드
     */
    private String cntrCd;

    /**
     * 설비 ID
     */
    private String eqpId;

    /**
     * 오토스토어 서버 IP
     */
    private String ifServIp;

    /**
     * 사용여부
     */
    private String useYn;

    private String eqpTypeCd;
}
