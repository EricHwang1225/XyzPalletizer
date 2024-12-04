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

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InterfaceException;
import com.lgcns.smartwcs.common.model.InterfaceJsonResponse;
import com.lgcns.smartwcs.common.utils.CommonConstants;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import com.lgcns.smartwcs.interfaces.model.SkuBcdMaster;
import com.lgcns.smartwcs.interfaces.model.SkuMaster;
import com.lgcns.smartwcs.interfaces.model.SkuMasterDataFormat;
import com.lgcns.smartwcs.interfaces.service.SkuMasterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * 재고실사 오더 처리를 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j

@RestController
@RequestMapping(value = "/interface")
public class SkuInterfaceController {

    @Autowired
    private SkuMasterService skuMasterService;

    @PostMapping(value = "/sendSkuMasterInfo", headers = "X-API-VERSION=1", produces = {"application/json"})
    @Operation(summary = "SKU 정보", description = "SKU 정보를 WMS에서 WCS로 전송함")
    public InterfaceJsonResponse receiveSkuInformation(
            @RequestBody SkuMasterDataFormat skuMasterDataFormat, InterfaceJsonResponse interfaceJsonResponse)
            throws InterfaceException {

        interfaceJsonResponse.setTrackingId(skuMasterDataFormat.getTrackingId());
        SkuMaster data = skuMasterDataFormat.getData();
        List<String> messageArray = receiveSkuInformation(data);

        log.info("========== WMS Sku 수신 로그 ============");
        log.info(skuMasterDataFormat.toString());

        if (messageArray.size() > 0) {
            throw new InterfaceException(skuMasterDataFormat.getTrackingId(), messageArray);
        }

        try {
            skuMasterDataFormat.getData().setTrackingId(skuMasterDataFormat.getTrackingId());
            skuMasterService.createSkuInfo(skuMasterDataFormat.getData());
            interfaceJsonResponse.setResultCode(0);
            interfaceJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());
        } catch (DataNotFoundException e) {
            interfaceJsonResponse.setResultCode(9);
            interfaceJsonResponse.setResultMessage("Sku Code: " + data.getSkuCd() + "의 " + e.getMessage());
        } catch (Exception e) {
            interfaceJsonResponse.setResultCode(9);
            interfaceJsonResponse.setResultMessage(CommonConstants.SYSTEM_ERROR);
        }

        return interfaceJsonResponse;
    }

    private List<String> receiveSkuInformation(SkuMaster data) {
        List<String> messageArray = new ArrayList<>();

        CommonUtils.validateParam(true, "coCd", data.getCoCd(), 30, messageArray);
        CommonUtils.validateParam(true, "cstCd", data.getCstCd(), 30, messageArray);
        CommonUtils.validateParam(true, "useYn", data.getUseYn(), 1, messageArray);
        CommonUtils.validateParam(true, "skuCd", data.getSkuCd(), 50, messageArray);
        CommonUtils.validateParam(true, "skuNm", data.getSkuNm(), 500, messageArray);

        if (!StringUtils.hasText(data.getRegDt())) {
            data.setRegDt(CommonUtils.getCurrentDate());
        } else if (data.getRegDt().length() != 14) {
            messageArray.add("regDt 는 14자리로 입력하세요");
        }

        if (!StringUtils.hasText(data.getRegId())) {
            data.setRegId(data.getTrackingId());
        } else if (data.getRegId().length() > 30) {
            messageArray.add("regId 은 30자리 이하로 입력하세요");
        }

        return receiveSkuInformation2(messageArray, data);
    }

    private List<String> receiveSkuInformation2(List<String> messageArray, SkuMaster data) {

        if (data.getDetail() == null || data.getDetail().isEmpty()) {
            messageArray.add("detail이 없습니다.");
        } else {
            // Detail
            List<SkuBcdMaster> details = data.getDetail();

            for (SkuBcdMaster skuBcdMaster : details) {

                if (!StringUtils.hasText(skuBcdMaster.getBcd())) {
                    messageArray.add("bcd 가 없습니다.");
                } else if (skuBcdMaster.getBcd().length() > 50) {
                    messageArray.add("bcd 는 50자리 이하로 입력하세요");
                }

                if (!StringUtils.hasText(skuBcdMaster.getUseYn())) {
                    messageArray.add("useYn 이 없습니다.");
                } else if (skuBcdMaster.getUseYn().length() != 1) {
                    messageArray.add("useYn 은 1자리로 입력하세요");
                }
            }
        }

        return messageArray;
    }



}
