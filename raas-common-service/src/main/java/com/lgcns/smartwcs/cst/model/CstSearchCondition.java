/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.cst.model;

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
public class CstSearchCondition {

    /**
     * 회사코드
     */
    private String coCd;

    /**
     * 화주코드
     */
    private String cstCd;

    /**
     * 화주이름
     */
    private String cstNm;
    
    /**
     * 사용여부
     */
    private String useYn;

    /**
     * Excel Download FileName
     */
    private String fileName;

    /**
     * 팝업에서 호출 여부
     */
    private String popUpYn;

}
