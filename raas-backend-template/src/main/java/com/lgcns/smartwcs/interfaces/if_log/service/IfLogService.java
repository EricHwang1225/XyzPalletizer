/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.interfaces.if_log.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.smartwcs.common.exception.InterfaceException;
import com.lgcns.smartwcs.common.model.InterfaceJsonResponse;
import com.lgcns.smartwcs.common.model.WmsResult;
import com.lgcns.smartwcs.interfaces.if_log.model.WcsIfLog;
import com.lgcns.smartwcs.interfaces.if_log.model.WmsIfLog;
import com.lgcns.smartwcs.interfaces.if_log.repository.WcsIfLogRepository;
import com.lgcns.smartwcs.interfaces.if_log.repository.WmsIfLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * <PRE>
 * 빈 정보를 조작하기 위한 Service 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IfLogService {

    /**
     * Sku Mapper
     */
    private final WmsIfLogRepository wmsIfLogRepository;

    private final WcsIfLogRepository wcsIfLogRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Async
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class, InterfaceException.class})
    public void saveWmsIfLog(String wmsIfNm, String wmsIfTrakNo, Object requestBody)  {

        String json = null;

        try {
            json = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        log.info(json);

         WmsIfLog wmsIfLog = WmsIfLog.builder()
                .wmsIfNm(wmsIfNm)
                .wmsIfTrakNo(wmsIfTrakNo)
                .wmsIfJson(json)
                .build();

        wmsIfLogRepository.save(wmsIfLog);
    }

    @Async
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class, InterfaceException.class})
    public void updateIfErrLog(InterfaceJsonResponse interfaceJsonResponse)  {

        List<WmsIfLog> allByWmsIfNo = wmsIfLogRepository.findAllByWmsIfTrakNo(interfaceJsonResponse.getTrackingId());
        for (WmsIfLog wmsIfLog : allByWmsIfNo) {
            wmsIfLog.setWcsIfYn("N");
            wmsIfLog.setWcsErrCd("9");
            wmsIfLog.setWcsErrMSG(interfaceJsonResponse.getResultMessage());
            wmsIfLogRepository.save(wmsIfLog);
        }
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public void saveWcsIfLog(String wcsIfNm, String json, String wcsIfTrackNo, WmsResult wmsResult, String pickNo)  {

        //TODO: 각 고객사에 맞게 변경
//        String wmsJson = "";
//        if(wmsResult.getData() != null) {
//            try {
//                wmsJson = objectMapper.writeValueAsString(wmsResult.getData());
//            } catch (JsonProcessingException e) {
//                log.error(e.getMessage());
//            }
//            log.info(wmsJson);
//        }

        WcsIfLog wcsIfLog = WcsIfLog.builder()
                .wcsIfNm(wcsIfNm)
                .wcsIfTrakNo(wcsIfTrackNo)
                .wcsIfJson(json)
                .pickNo(pickNo)
//                .wmsIfYn(wmsResult.isSuccess()?"Y":"N")
//                .wmsIfStatCd(wmsResult.getResultCode().toString())
//                .wmsIfMsg(wmsResult.getMsg().length() < 1000 ? wmsResult.getMsg() : wmsResult.getMsg().substring(0, 999))
//                .wmsIfJson(wmsJson)
                .regId("WCS IF")
                .wmsIfCnt(1)
                .build();

        wcsIfLogRepository.save(wcsIfLog);
    }


}
