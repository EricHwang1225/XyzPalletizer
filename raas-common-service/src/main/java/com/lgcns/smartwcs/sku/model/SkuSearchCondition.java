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
public class SkuSearchCondition {

    /**
     * 회사코드
     */
    private String coCd;

    /**
     * 화주코드(고객사)
     */
    private String cstCd;

    /**
     * Sku 코드
     */
    private String skuCd;

    /**
     * Sku 명
     */
    private String skuNm;

    /**
     * Sku Barcode
     */
    private String bcd;
    
    /**
     * 사용여부
     */
    private String useYn;

    /**
     * Barcode 사용여부
     */
    private String bcdUseYn;

    /**
     * Excel Download FileName
     */
    private String fileName;

    /**
     * Selected Sku Id
     */
    private String selectedCstCd;
    private String selectedSkuCd;
}
