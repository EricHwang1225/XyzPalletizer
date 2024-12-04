/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.repository;

import com.lgcns.smartwcs.common.model.AutoStoreServer;
import com.lgcns.smartwcs.common.model.WmsServer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <PRE>
 * 재고실사 예정 Mapper 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Repository
@Mapper
public interface ServerIPConfigMapper {

    /**
     * 새로운 재고실사 오더 UID를 얻어온다.
     * @return
     */
    List<AutoStoreServer> getAutostoreIpList();

    List<WmsServer> getWmsUrlList();

}
