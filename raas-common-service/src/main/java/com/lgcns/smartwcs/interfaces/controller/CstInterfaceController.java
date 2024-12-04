/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.interfaces.controller;

import com.lgcns.smartwcs.common.exception.InterfaceException;
import com.lgcns.smartwcs.common.model.InterfaceJsonResponse;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import com.lgcns.smartwcs.interfaces.model.CstMaster;
import com.lgcns.smartwcs.interfaces.model.CstMasterDataFormat;
import com.lgcns.smartwcs.interfaces.service.CstMasterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * 화주 정보 처리를 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j

@RestController
@RequestMapping(value = "/interface/sendOwnerMasterInfo")
public class CstInterfaceController {

    @Autowired
    private CstMasterService cstMasterService;

    @PostMapping(headers = "X-API-VERSION=1", produces = {"application/json"})
    @Operation(summary = "화주 정보", description = "화주 정보를 WMS에서 WCS로 전송함")
    public InterfaceJsonResponse receiveCstInformation(
            @RequestBody CstMasterDataFormat cstMasterDataFormat, InterfaceJsonResponse interfaceJsonResponse)
            throws InterfaceException {

        interfaceJsonResponse.setTrackingId(cstMasterDataFormat.getTrackingId());
        ArrayList<String> messageArray = receiveCstInformationValidation(cstMasterDataFormat);

        log.info("========== WMS Cst 수신 로그 ============");
        log.info(cstMasterDataFormat.toString());

        if (messageArray.size() > 0) {
            throw new InterfaceException(cstMasterDataFormat.getTrackingId(), messageArray);
        }


        cstMasterDataFormat.getData().setTrackingId(cstMasterDataFormat.getTrackingId());
        cstMasterService.createCstInfo(cstMasterDataFormat.getData());
        interfaceJsonResponse.setResultCode(0);
        interfaceJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());


        return interfaceJsonResponse;
    }

    private ArrayList<String> receiveCstInformationValidation(CstMasterDataFormat cstMasterDataFormat) {
        ArrayList<String> messageArray = new ArrayList<>();

        CstMaster data = cstMasterDataFormat.getData();

        CommonUtils.validateParam(true, "coCd", data.getCoCd(), 30, messageArray);
        CommonUtils.validateParam(true, "cstCd", data.getCstCd(), 30, messageArray);
        CommonUtils.validateParam(true, "cstNm", data.getCstNm(), 50, messageArray);
        CommonUtils.validateParam(true, "useYn", data.getUseYn(), 1, messageArray);

        if (!StringUtils.hasText(data.getRegDt())) {
            data.setRegDt(CommonUtils.getCurrentDate());
        } else if (data.getRegDt().length() != 14) {
            messageArray.add("regDt 는 14자리로 입력하세요");
        }

        if (!StringUtils.hasText(data.getRegId())) {
            data.setRegId(cstMasterDataFormat.getTrackingId());
        } else if (data.getRegId().length() > 30) {
            messageArray.add("regId 은 30자리 이하로 입력하세요");
        }

        return messageArray;
    }


}
