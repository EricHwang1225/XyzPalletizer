/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.dash_board.service;

import com.lgcns.smartwcs.dash_board.model.Workload;
import com.lgcns.smartwcs.dash_board.repository.WorkloadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <PRE>
 * 화면별 작업량 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
public class WorkloadService {

    /**
     * 작업량  Mapper
     */
    @Autowired
    private WorkloadMapper workloadMapper;
    /**
     * 각 화면별 작업량을 조회 한다.
     * @param workload 검색 조건
     * @return 각 화면별 작업량
     */
    public Map<String, Object> workload(Workload workload) {

        Map<String, Object> resultMap = new HashMap<>();

        Integer inbdWorkload = workloadMapper.getInbdWorkload(workload);
        Integer obndWorkload = workloadMapper.getObndWorkload(workload);
        Integer sttkWorkload = workloadMapper.getSttkWorkload(workload);

        resultMap.put("inbdWorkload", inbdWorkload == null ? 0 : inbdWorkload);
        resultMap.put("obndWorkload", obndWorkload  == null ? 0 : obndWorkload);
        resultMap.put("sttkWorkload", sttkWorkload  == null ? 0 : sttkWorkload);

        resultMap.put("PRS0101", inbdWorkload == null ? 0 : inbdWorkload);
        resultMap.put("PRS0102", obndWorkload  == null ? 0 : obndWorkload);
        resultMap.put("PRS0103", sttkWorkload  == null ? 0 : sttkWorkload);

        return resultMap;
    }

}
