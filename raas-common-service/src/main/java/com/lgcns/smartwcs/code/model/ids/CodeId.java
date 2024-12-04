/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.code.model.ids;

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
public class CodeId implements Serializable {

    /**
     * 회사 코드
     */
    private String coCd;
    /**
     * 대분류 코드
     */
    private String comHdrCd;
    /**
     * 상세 코드.
     */
    private String comDtlCd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeId that = (CodeId) o;
        return coCd.equals(that.coCd) && comHdrCd.equals(that.comHdrCd) && comDtlCd.equals(that.comDtlCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coCd, comHdrCd, comDtlCd);
    }
}
