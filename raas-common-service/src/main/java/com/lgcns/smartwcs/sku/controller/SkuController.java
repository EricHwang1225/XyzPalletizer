/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.sku.controller;

import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import com.lgcns.smartwcs.sku.model.SkuBcdSearchCondition;
import com.lgcns.smartwcs.sku.model.SkuSearchCondition;
import com.lgcns.smartwcs.sku.service.SkuBcdService;
import com.lgcns.smartwcs.sku.service.SkuService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Sku 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/sku")
public class SkuController {

    /**
     * Sku Service 객체
     */
    @Autowired
    private SkuService skuService;

    /**
     * Sku Barcode  서비스 객체.
     */
    @Autowired
    private SkuBcdService skuBcdService;


    /**
     * Sku 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 Sku 목록
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "Sku 목록 조회", description = "Sku 목록을 조회한다.")
    public CommonJsonResponse list(SkuSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        condition.setCstCd(CommonUtils.replacePercentAndUnderbar(condition.getCstCd()));
        condition.setSkuCd(CommonUtils.replacePercentAndUnderbar(condition.getSkuCd()));
        condition.setSkuNm(CommonUtils.replacePercentAndUnderbar(condition.getSkuNm()));
        condition.setBcd(CommonUtils.replacePercentAndUnderbar(condition.getBcd()));

        commonJsonResponse.setData(skuService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @GetMapping(value = "/skuBcd", produces = {"application/json"})
    @Operation(summary = "Sku Barcode 조회", description = "Sku Barcode 목록을 조회한다.")
    public CommonJsonResponse list(SkuBcdSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(skuBcdService.getUnPagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }


}
