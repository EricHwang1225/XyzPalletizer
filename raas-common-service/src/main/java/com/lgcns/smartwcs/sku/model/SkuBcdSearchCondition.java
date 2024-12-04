/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.sku.model;


import lombok.*;

/**
 * <PRE>
 * Sku Master 검색 조건 클래스.
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
public class SkuBcdSearchCondition {

    /**
     * 회사코드
     */
    private String coCd;

    /**
     * 화주
     */
    private String cstCd;

    /**
     * 상품코드
     */
    private String skuCd;

    /**
     * 상품바코드
     */
    private String bcd;

    /**
     * 사용 여부
     */
    private String bcdUseYn;

    /**
     * 사용 여부
     */
    private String useYnDt;

    /**
     * Selected Sku Id
     */
    private String selectedCstCd;
    private String selectedSkuCd;
}
