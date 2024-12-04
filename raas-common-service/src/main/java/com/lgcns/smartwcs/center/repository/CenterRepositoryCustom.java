/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.center.repository;

import com.lgcns.smartwcs.center.model.Center;

import java.util.List;

/**
 * <PRE>
 * Center 검색용 QueryDSL Custom 인터페이스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
public interface CenterRepositoryCustom {

    List<Center> findAllbyCenterNm(String coCd, String cntrNm);
}
