/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.center.controller;

import com.lgcns.smartwcs.center.model.CenterSearchCondition;
import com.lgcns.smartwcs.center.service.CenterService;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Center 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/center")
public class CenterController {

    /**
     * Center Service 객체
     */
    @Autowired
    private CenterService centerService;

    /**
     * Tenant별 센터 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건*
     * @return 페이징된 사용자 목록
     */
    @GetMapping(value = "/getTenantCntrUnpagedList", produces = {"application/json", "application/xml"})
    @Operation(summary = "Tenant별 센터 목록 조회", description = "센터 목록을 조회한다.")
    public CommonJsonResponse getTenantCntrUnpagedList(CenterSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        condition.setCoCd(CommonUtils.replacePercentAndUnderbar(condition.getCoCd()));
        condition.setSearchCntrCd(CommonUtils.replacePercentAndUnderbar(condition.getSearchCntrCd()));
        condition.setCntrNm(CommonUtils.replacePercentAndUnderbar(condition.getCntrNm()));
        condition.setUserId(CommonUtils.replacePercentAndUnderbar(condition.getUserId()));
        condition.setUserNm(CommonUtils.replacePercentAndUnderbar(condition.getUserNm()));

        commonJsonResponse.setData(centerService.getTenantCntrUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @GetMapping(value = "/getTenantCntrListForLogin", produces = {"application/json", "application/xml"})
    @Operation(summary = "로그인 화면에서 Tenant별 센터 목록 조회", description = "센터 목록을 조회한다.")
    public CommonJsonResponse getTenantCntrListForLogin(CenterSearchCondition condition, CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData(centerService.getTenantCntrListForLogin(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }


}
