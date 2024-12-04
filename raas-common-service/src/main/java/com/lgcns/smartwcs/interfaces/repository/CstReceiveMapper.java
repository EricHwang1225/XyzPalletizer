/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.interfaces.repository;

import com.lgcns.smartwcs.interfaces.model.CstMaster;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * Cst Master 수신 정보 연계 Mapper 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Repository
@Mapper
public interface CstReceiveMapper {

    /**
     * Sku Master를 저장한다.
     * @param cstMaster
     */
    void save(CstMaster cstMaster);

}
