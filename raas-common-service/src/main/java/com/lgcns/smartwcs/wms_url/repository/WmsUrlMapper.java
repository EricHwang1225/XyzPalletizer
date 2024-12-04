/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.wms_url.repository;


import com.lgcns.smartwcs.wms_url.model.WmsUrl;
import com.lgcns.smartwcs.wms_url.model.WmsUrlSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * <PRE>
 * Tenant Mapper 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Repository
@Mapper
public interface WmsUrlMapper {

    List<Map<String, Object>> getWmsUrlList(WmsUrlSearchCondition wmsUrlSearchCondition);

    Integer getWmsUrlTotalCnt(WmsUrlSearchCondition wmsUrlSearchCondition);

    void saveWmsUrl(WmsUrl wmsUrl);

    List<Map<String, Object>> getWmsUrlExcelList(WmsUrlSearchCondition condition);
}
