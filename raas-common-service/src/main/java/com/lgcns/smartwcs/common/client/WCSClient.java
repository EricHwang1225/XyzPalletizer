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

import com.lgcns.smartwcs.common.client.model.WCSResponse;
import com.lgcns.smartwcs.common.model.WcsResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

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
@RequiredArgsConstructor
public class WCSClient {


    private final RestTemplate restTemplate;

    private final String requestUrl = "http://localhost:8090/interface/common-service/";

    public static final String SEND_OWNER_MASTER = "sendOwnerMasterInfo";
    public static final String SEND_SKU_MASTER = "sendSkuMasterInfo";


    public static HashMap<String, HashMap<String, String>> wmsUrlList = new HashMap<>();

    public WCSResponse postInterface(String url, Object request) {
        WCSResponse response = null;

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-VERSION", "1");
            HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
            response = restTemplate.postForObject(requestUrl + url, entity, WCSResponse.class);

            if (response == null || response.getResultCode() == null) {
                response = WCSResponse.builder()
                        .resultCode(-1)
                        .resultMessage("null")
                        .build();
            }
        } catch (RestClientException rce) {
            // TODO 에러 코드 정리 필요.
            response = WCSResponse.builder()
                    .resultCode(-1)
                    .resultMessage(rce.getMessage())
                    .build();
        }


        return response;
    }

    public WcsResult callPostUrl(String serverUrl, String json) {
        WcsResult wcsResult = new WcsResult();
        wcsResult.setCnclCnt(1);

        int cnt = 0;
        while (cnt < 3) {
            cnt++;
            WCSResponse res = this.postInterface(serverUrl, json);

            if (res.getResultCode() == -1) {
                wcsResult.setSuccess(false);
                wcsResult.setMsg(res.getResultMessage());

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error(e.getMessage());
                }
            } else if (res.getResultCode() == 0) {
                wcsResult.setSuccess(true);
                wcsResult.setMsg(res.getResultMessage());
                break;
            } else {
                wcsResult.setSuccess(false);
                wcsResult.setMsg(res.getResultMessage());
                break;

            }
        }

        wcsResult.setCnclCnt(cnt);

        return wcsResult;
    }
}
