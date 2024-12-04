/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.role.model;

import lombok.*;

/**
 * <PRE>
 * Role Menu 검색 조건 클래스.
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
public class RoleMenuSearchCondition {

    /**
     * Company Code
     */
    private String coCd;

    /**
     * Center Code
     */
    private String cntrCd;

    /**
     * role Code
     */
    private String roleCd;

    private String langCd;
}
