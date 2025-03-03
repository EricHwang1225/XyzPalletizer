/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.center.service;

import com.lgcns.smartwcs.center.model.Center;
import com.lgcns.smartwcs.center.model.CenterSearchCondition;
import com.lgcns.smartwcs.center.model.ids.CenterId;
import com.lgcns.smartwcs.center.repository.CenterMapper;
import com.lgcns.smartwcs.center.repository.CenterRepository;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * Center 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
public class CenterService {

    /**
     * Center Repository 객체
     */
    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private CenterMapper centerMapper;


    public List<Map<String, Object>> getTenantCntrUnpagedList(CenterSearchCondition condition) {
        condition.setCntrCd(condition.getSearchCntrCd());
        return centerMapper.getTenantCntrUnpagedList(condition);
    }

    public List<Map<String, Object>> getTenantCntrListForLogin(CenterSearchCondition condition) {
        return centerMapper.getTenantCntrListForLogin(condition);
    }

    /**
     * 특정 키의 Center 정보 조회
     *
     * @param id Center의 복합 키
     * @return 조회된 Center 객체
     * @throws DataNotFoundException Center를 찾을 수 없을때 발생
     */
    public Center get(CenterId id) throws DataNotFoundException {

        return centerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Center를 찾을 수 없습니다"));
    }


}
