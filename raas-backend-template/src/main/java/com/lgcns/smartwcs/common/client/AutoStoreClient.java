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

import com.lgcns.smartwcs.common.client.model.AutoStoreResponse;
import com.lgcns.smartwcs.common.model.AutoStoreServer;
import com.lgcns.smartwcs.common.repository.ServerIPConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * AutoStore와 통신하기 위한 HttpClient 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j
@Service
public class AutoStoreClient {

    public static final String TASK_IF_URL = "taskIfUrl";
    public static final String BIN_IF_URL = "binIfUrl";

    public static final String SUCCESS_CODE = "0000";
    private static final String AS_ERR_CODE = "-1000";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServerIPConfigMapper serverIPConfigMapper;

    private static final String HTTP = "http://";

    public static final HashMap<String, HashMap<String, String>> autoStoreServerUrlList = new HashMap<>();

    @PostConstruct
    public void init() {
//        changeAutoStoreUrl();
    }

    public String postInterface(String url, Object request) {
        AutoStoreResponse response = null;

        try {
            response = restTemplate.postForObject(url, request, AutoStoreResponse.class);
            if(response == null) {
                return AS_ERR_CODE;
            }
            response.getParams();
            if(response.getFault() == null) {
                return SUCCESS_CODE;
            }
            else {
                return response.getFault().getCode();
            }
        }
        catch(RestClientException rce) {
            // TODO Exception 별 에러 코드 리턴.

            return AS_ERR_CODE;
        }
    }

    /**
     * openbin 호출시에 사용.(응답이 있을 경우 사용함.)
     * @param url
     * @param request
     * @return
     */
    public Map<String, Object> postGetResponseParamsInterface(String url, Object request) {
        AutoStoreResponse response = null;

        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();

        try {
            response = restTemplate.postForObject(url, request, AutoStoreResponse.class);
            if(response == null) {
                returnMap.put(MessageEnum.RESULT_CODE.getValue(), AS_ERR_CODE);
                return returnMap;
            }

            if(response.getFault() == null) {

                returnMap.put(MessageEnum.RESULT_CODE.getValue(), SUCCESS_CODE);

                if(response.getParams().getBinId() != null
                        && !response.getParams().getBinId().isEmpty()) {
                    dataMap.put("binId", response.getParams().getBinId());
                }

                if(response.getParams().getTaskId() != null
                        && !response.getParams().getTaskId().isEmpty()) {
                    dataMap.put("taskId", response.getParams().getTaskId());
                }

                if(response.getParams().getBins() != null
                        && response.getParams().getBins().getBin().getBinMode() != null
                        && !response.getParams().getBins().getBin().getBinMode().isEmpty() ) {

                    dataMap.put("binMode", response.getParams().getBins().getBin().getBinMode());
                    dataMap.put("portId", response.getParams().getBins().getBin().getPortId());
                }

                returnMap.put("data", dataMap);

                return returnMap;
            } else {

                returnMap.put(MessageEnum.RESULT_CODE.getValue(), response.getFault().getCode());
                return returnMap;
            }
        } catch(RestClientException rce) {
            // TODO Exception 별 에러 코드 리턴.
            log.error(rce.getMessage(), rce);
            returnMap.put(MessageEnum.RESULT_CODE.getValue(), AS_ERR_CODE);
            return returnMap;
        }
    }

    public void changeAutoStoreUrl(){
        List<AutoStoreServer> autoStoreServerList = serverIPConfigMapper.getAutostoreIpList();

        autoStoreServerUrlList.clear();
        for (AutoStoreServer autoStoreServer: autoStoreServerList) {

            HashMap<String, String> autoStoreActionUrlList = new HashMap<>();
            autoStoreActionUrlList.put(TASK_IF_URL, new StringBuffer(HTTP).append(autoStoreServer.getAsIfIp()).append("/AsInterfaceHttp/AutoStoreHttpInterface.aspx").toString());
            autoStoreActionUrlList.put(BIN_IF_URL, new StringBuffer(HTTP).append(autoStoreServer.getAsIfIp()).append("/AsInterfaceHttp/BinInterface.aspx").toString());

            autoStoreServerUrlList.put(autoStoreServer.getServerKey(), autoStoreActionUrlList);
        }
    }
}
