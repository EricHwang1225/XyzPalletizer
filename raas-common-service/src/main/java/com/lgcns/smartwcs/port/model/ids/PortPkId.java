/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.port.model.ids;

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
public class PortPkId implements Serializable {

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
     * 포트 ID
     */
    private String portId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortPkId portPkId1 = (PortPkId) o;
        return coCd.equals(portPkId1.coCd) && cntrCd.equals(portPkId1.cntrCd) && eqpId.equals(portPkId1.eqpId) && portId.equals(portPkId1.portId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coCd, cntrCd, eqpId, portId);
    }
}
