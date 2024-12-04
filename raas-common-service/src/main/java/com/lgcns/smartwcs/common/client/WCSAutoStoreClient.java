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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.smartwcs.common.client.model.WCSAutoStoreResponse;
import com.lgcns.smartwcs.common.utils.CommonConstants;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum.*;

/**
 * <PRE>
 * WCS와 통신하기 위한 HttpClient 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@Slf4j
public class WCSAutoStoreClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${httpclient.target.wcs.server}")
    private String WCS_SERVER;

    public static final String CREATE_TASK_GROUP = "createTaskGroup";
    public static final String CANCEL_TASK_GROUP = "cancelTaskGroup";
    public static final String CANCEL_TASK = "cancelTask";
    public static final String GET_TASK_INFO = "getTaskInfo";
    public static final String UPDATE_BIN = "updateBin";
    public static final String CLOSE_PORT = "closePort";
    public static final String OPEN_PORT = "openPort";
    public static final String CLOSE_BIN = "closeBin";
    public static final String OPEN_BIN = "openBin";

    public static final Map<String, String> wcsInterface = new HashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        wcsInterface.put(CREATE_TASK_GROUP, WCS_SERVER + CREATE_TASK_GROUP);
        wcsInterface.put(CANCEL_TASK_GROUP, WCS_SERVER + CANCEL_TASK_GROUP);
        wcsInterface.put(CANCEL_TASK, WCS_SERVER + CANCEL_TASK);
        wcsInterface.put(GET_TASK_INFO, WCS_SERVER + GET_TASK_INFO);
        wcsInterface.put(UPDATE_BIN, WCS_SERVER + UPDATE_BIN);
        wcsInterface.put(CLOSE_PORT, WCS_SERVER + CLOSE_PORT);
        wcsInterface.put(OPEN_PORT, WCS_SERVER + OPEN_PORT);
        wcsInterface.put(CLOSE_BIN, WCS_SERVER + CLOSE_BIN);
        wcsInterface.put(OPEN_BIN, WCS_SERVER + OPEN_BIN);
    }

    public WCSAutoStoreResponse postInterface(String url, Object request) {
        WCSAutoStoreResponse response = null;

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);
            response = restTemplate.postForObject(url, entity, WCSAutoStoreResponse.class);
        } catch (RestClientException rce) {
            // TODO 에러 코드 정리 필요.
            response = WCSAutoStoreResponse.builder()
                    .resultCode(-1)
                    .resultMessage(rce.getMessage())
                    .build();
        }

        return response;
    }

    public Map<String, Object> callClosePortAndGet(Object request) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> autoStoreMap = new HashMap<>();

        try {
            String json = objectMapper.writeValueAsString(request);
            WCSAutoStoreResponse res = this.postInterface(WCSAutoStoreClient.wcsInterface.get(WCSAutoStoreClient.CLOSE_PORT), json);

            if (res.getResultCode() == 100) {
                autoStoreMap.put(STATUS_CODE.getValue(), String.valueOf(res.getData().get("resultCode")));
                autoStoreMap.put("msg", String.valueOf(res.getData().get("resultMessage")));
            } else {
                autoStoreMap.put(STATUS_CODE.getValue(), String.valueOf(res.getResultCode()));
                autoStoreMap.put("msg", res.getResultMessage());
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        returnMap.put("step", CLOSE_PORT);
        returnMap.put(CommonConstants.MessageEnum.STATUS.getValue(), autoStoreMap);

        return returnMap;
    }

    public Map<String, Object> callClosePort(String json) {
        Map<String, Object> closePortMap = new HashMap<>();

        String statusCd = "";
        String msg = "";

        WCSAutoStoreResponse res = this.postInterface(WCSAutoStoreClient.wcsInterface.get(WCSAutoStoreClient.CLOSE_PORT), json);

        if (res.getResultCode() == 100) {
            statusCd = String.valueOf(res.getData().get(RESULT_CODE.getValue()));
            msg = String.valueOf(res.getData().get(RESULT_MESSAGE.getValue()));
        } else {
            statusCd = String.valueOf(res.getResultCode());
            msg = res.getResultMessage();
        }

        closePortMap.put(STATUS_CODE.getValue(), statusCd);
        closePortMap.put("msg", msg);

        return closePortMap;
    }

    public Map<String, Object> callOpenPortAndGet(Object request) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> autoStoreMap = new HashMap<>();

        try {
            String json = objectMapper.writeValueAsString(request);
            WCSAutoStoreResponse res = this.postInterface(WCSAutoStoreClient.wcsInterface.get(WCSAutoStoreClient.OPEN_PORT), json);

            if (res.getResultCode() == 100) {
                autoStoreMap.put(STATUS_CODE.getValue(), String.valueOf(res.getData().get("resultCode")));
                autoStoreMap.put("msg", String.valueOf(res.getData().get("resultMessage")));
            } else {
                autoStoreMap.put(STATUS_CODE.getValue(), String.valueOf(res.getResultCode()));
                autoStoreMap.put("msg", res.getResultMessage());
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        returnMap.put("step", OPEN_PORT);
        returnMap.put("status", autoStoreMap);

        return returnMap;
    }

    public Map<String, Object> callOpenPort(String json) {
        Map<String, Object> openPortMap = new HashMap<>();

        String statusCd = "";
        String msg = "";

        WCSAutoStoreResponse res = this.postInterface(WCSAutoStoreClient.wcsInterface.get(WCSAutoStoreClient.OPEN_PORT), json);

        if (res.getResultCode() == 100) {
            statusCd = String.valueOf(res.getData().get(RESULT_CODE.getValue()));
            msg = String.valueOf(res.getData().get(RESULT_MESSAGE.getValue()));
        } else {
            statusCd = String.valueOf(res.getResultCode());
            msg = res.getResultMessage();
        }

        openPortMap.put(STATUS_CODE.getValue(), statusCd);
        openPortMap.put("msg", msg);

        return openPortMap;
    }

    public Map<String, Object> callCloseBin(String json) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> autoStoreMap = new HashMap<>();

        String statusCd = "";
        String msg = "";

        WCSAutoStoreResponse res = this.postInterface(WCSAutoStoreClient.wcsInterface.get(WCSAutoStoreClient.CLOSE_BIN), json);

        if (res.getResultCode() == 100) {
            statusCd = String.valueOf(res.getData().get(RESULT_CODE.getValue()));
            msg = String.valueOf(res.getData().get(RESULT_MESSAGE.getValue()));
        } else {
            statusCd = String.valueOf(res.getResultCode());
            msg = res.getResultMessage();
        }

        returnMap.put("step", CLOSE_BIN);
        autoStoreMap.put("statusCd", statusCd);
        autoStoreMap.put("msg", msg);
        returnMap.put("status", autoStoreMap);

        return returnMap;
    }

    public Map<String, Object> callOpenBin(String json) {
        Map<String, Object> openBinMap = new HashMap<>();

        String statusCd = "";
        String msg = "";

        WCSAutoStoreResponse res = this.postInterface(WCSAutoStoreClient.wcsInterface.get(WCSAutoStoreClient.OPEN_BIN), json);

        if (res.getResultCode() == 100) {
            statusCd = String.valueOf(res.getData().get(RESULT_CODE.getValue()));
            msg = String.valueOf(res.getData().get(RESULT_MESSAGE.getValue()));
        } else {
            statusCd = String.valueOf(res.getResultCode());
            msg = res.getResultMessage();
        }

        openBinMap.put(STATUS_CODE.getValue(), statusCd);
        openBinMap.put("msg", msg);
        openBinMap.put("data", res.getData().get("data"));

        return openBinMap;
    }

    public Map<String, Object> callUpdateBin(Object request) {
        Map<String, Object> updateBinMap = new HashMap<>();

        String statusCd = "";
        String msg = "";

        try {
            String json = objectMapper.writeValueAsString(request);
            WCSAutoStoreResponse res = this.postInterface(WCSAutoStoreClient.wcsInterface.get(WCSAutoStoreClient.UPDATE_BIN), json);

            if (res.getResultCode() == 100) {
                statusCd = String.valueOf(res.getData().get(RESULT_CODE.getValue()));
                msg = String.valueOf(res.getData().get(RESULT_MESSAGE.getValue()));
            } else {
                statusCd = String.valueOf(res.getResultCode());
                msg = res.getResultMessage();
            }

            updateBinMap.put(STATUS_CODE.getValue(), statusCd);
            updateBinMap.put("msg", msg);
            updateBinMap.put("data", res.getData() == null ? "AutoStore error" : res.getData().get("data"));
            updateBinMap.put(RESULT_CODE.getValue(), res.getResultCode());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return updateBinMap;
    }

    public Map<String, Object> callCreateTaskGroup(Object request) {
        Map<String, Object> createMap = new HashMap<>();

        String statusCd = "";
        String msg = "";

        try {
            String json = objectMapper.writeValueAsString(request);
            WCSAutoStoreResponse res = this.postInterface(WCSAutoStoreClient.wcsInterface.get(WCSAutoStoreClient.CREATE_TASK_GROUP), json);

            if (res.getResultCode() == 100) {
                statusCd = String.valueOf(res.getData().get(RESULT_CODE.getValue()));
                msg = String.valueOf(res.getData().get(RESULT_MESSAGE.getValue()));
            } else {
                statusCd = String.valueOf(res.getResultCode());
                msg = res.getResultMessage();
            }

            createMap.put(STATUS_CODE.getValue(), statusCd);
            createMap.put("msg", msg);
            createMap.put("data", res.getData().get("data"));
            createMap.put(RESULT_CODE.getValue(), res.getResultCode());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return createMap;
    }

    public void cancelTask(String json) {
        int cnt = 0;

        while (cnt < 5) {
            WCSAutoStoreResponse res = this.postInterface(WCSAutoStoreClient.wcsInterface.get(WCSAutoStoreClient.CANCEL_TASK_GROUP), json);

            if (res.getResultCode() != 100) {
                log.error(res.getResultMessage());
                ++cnt;
                CommonUtils.sleep(10000);
            } else {
                break;
            }
        }
    }
}
