/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.req.sku.model.ids;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * <PRE>
 * 공통 코드 조합키 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuReqId implements Serializable {

    private String coCd;
    private String cstCd;
    private String skuCd;
    private String bcdNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkuReqId that = (SkuReqId) o;
        return coCd.equals(that.coCd) && cstCd.equals(that.cstCd) && skuCd.equals(that.skuCd)
                && bcdNo.equals(that.bcdNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coCd, cstCd, skuCd, bcdNo);
    }
}
