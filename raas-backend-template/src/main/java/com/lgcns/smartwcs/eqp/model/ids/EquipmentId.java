/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.eqp.model.ids;

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
public class EquipmentId implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentId EquipmentId1 = (EquipmentId) o;
        return coCd.equals(EquipmentId1.coCd) && cntrCd.equals(EquipmentId1.cntrCd) && eqpId.equals(EquipmentId1.eqpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coCd, cntrCd, eqpId);
    }
}
