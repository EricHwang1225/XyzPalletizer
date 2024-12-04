/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.user.model.ids;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * <PRE>
 * 사용자 조합 키 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPkId implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPkId userPkId1 = (UserPkId) o;
        return coCd.equals(userPkId1.coCd) && cntrCd.equals(userPkId1.cntrCd) && userId.equals(userPkId1.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coCd, cntrCd, userId);
    }
}
