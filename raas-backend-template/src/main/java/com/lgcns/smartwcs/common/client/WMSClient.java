/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.client;

import com.lgcns.smartwcs.common.client.model.WMSResponse;
import com.lgcns.smartwcs.common.model.WmsResult;
import com.lgcns.smartwcs.common.model.WmsServer;
import com.lgcns.smartwcs.common.repository.ServerIPConfigMapper;
import com.lgcns.smartwcs.interfaces.if_log.service.IfLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

/**
 * <PRE>
 * WMS와 통신하기 위한 HttpClient 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j
@Service
public class WMSClient {

    private final RestTemplate sslIgnoreRestTemplate;

    private final ServerIPConfigMapper serverIPConfigMapper;

    private final IfLogService ifLogService;

    //Lombok을 쓸경우 @Qualifier가 정상 작동하지 않는다. lombok.config를 사용해야하는데 귀찮아서 생성자를 직접 선언하여 DI를 해결하였다.
    public WMSClient(@Qualifier("sslIgnoreRestTemplate") RestTemplate sslIgnoreRestTemplate, ServerIPConfigMapper serverIPConfigMapper, IfLogService ifLogService) {
        this.sslIgnoreRestTemplate = sslIgnoreRestTemplate;
        this.serverIPConfigMapper = serverIPConfigMapper;
        this.ifLogService = ifLogService;
    }

    //TODO: 각 설비에 맞게 WMS 인터페이스 URL Key 수정
    public static final String INBD_IF_URL = "inbdIfUrl";
    public static final String OBND_IF_URL = "obndIfUrl";
    public static final String STTK_IF_URL = "sttkIfUrl";
    public static final String ABNORMAL_BIN_IF_URL = "abnormalBinIfUrl";


    public static HashMap<String, HashMap<String, String>> wmsUrlList = new HashMap<>();

    @PostConstruct
    public void init() {

//        List<WmsServer> wmsFullUrlList = serverIPConfigMapper.getWmsUrlList();
//
//        String serverKey = null;
//        HashMap<String, String> wmsActionUrlList = new HashMap<>();
//        for (WmsServer wmsServer : wmsFullUrlList) {
//            if (serverKey != null && !serverKey.equals(wmsServer.getServerKey())) {
//                wmsUrlList.put(serverKey, wmsActionUrlList);
//                wmsActionUrlList = new HashMap<>();
//            }
//
//            wmsActionUrlList.put(wmsServer.getWmsIfId(), wmsServer.getWmsIfUrl());
//
//            serverKey = wmsServer.getServerKey();
//        }
//
//        wmsUrlList.put(serverKey, wmsActionUrlList);
    }

    public WMSResponse postInterface(String url, Object request) {
        WMSResponse response = null;

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
            response = sslIgnoreRestTemplate.postForObject(url, entity, WMSResponse.class);

            if (response == null || response.getResultCode() == null) {
                response = WMSResponse.builder()
                        .resultCode(-1)
                        .resultMessage("null")
                        .build();
            }
        } catch (RestClientException rce) {
            // TODO 에러 코드 정리 필요.
            response = WMSResponse.builder()
                    .resultCode(-1)
                    .resultMessage(rce.getMessage())
                    .build();
        }


        return response;
    }

    public WmsResult callPostUrl(String serverUrl, String wmsIfNm, String json, String wmsIfTrakNo, String pickNo) {
        WmsResult wmsResult = new WmsResult();

        int cnt = 0;
        while (cnt < 3) {
            WMSResponse res = this.postInterface(serverUrl, json);

            if (res.getResultCode() != 0) {
                wmsResult.setSuccess(false);
                wmsResult.setMsg(res.getResultMessage());

                cnt++;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error(e.getMessage());
                }
            } else {
                wmsResult.setSuccess(true);
                wmsResult.setMsg(res.getResultMessage());
                break;
            }
        }

        //TODO: 각 고객사에 맞는 IF LOG 만들 것
        ifLogService.saveWcsIfLog(wmsIfNm, json, wmsIfTrakNo, wmsResult, pickNo);

        return wmsResult;
    }
}
