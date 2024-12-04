/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.tenant.controller;

import com.lgcns.smartwcs.common.exception.CommonException;
import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import com.lgcns.smartwcs.tenant.model.TenantDetail;
import com.lgcns.smartwcs.tenant.model.TenantSearchCondition;
import com.lgcns.smartwcs.tenant.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Tenant 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/tenant")
public class TenantController {

    /**
     * Tenant Service 객체
     */
    @Autowired
    private TenantService tenantService;


    /**
     * Tenant 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 Tenant 목록
     */
    @GetMapping(value = "tenantTreeList", produces = {"application/json"})
    @Operation(summary = "Tenant Tree 목록 조회", description = "Tenant Tree 목록 조회한다.")
    public CommonJsonResponse treeList(TenantSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        condition.setCoNm(CommonUtils.replacePercentAndUnderbar(condition.getCoNm()));

        commonJsonResponse.setData(tenantService.getTreeList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * Tenant 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 Tenant 목록
     */
    @GetMapping(value = "/selectedDetail", produces = {"application/json"})
    @Operation(summary = "Tenant Tree 선택 시 나오는 목록 조회", description = "Tenant Tree 선택 시 나오는 목록을 조회한다.")
    public CommonJsonResponse selectedDetailList(TenantSearchCondition condition, CommonJsonResponse commonJsonResponse)
            throws CommonException {

        if (condition.getTreeType() != null && !condition.getTreeType().equals("")) {
            commonJsonResponse.setData(tenantService.getSelectedDetailList(condition));
        }
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * Tenant를 추가한다.
     *
     * @param tenantDetail 추가할 Tenant 정보
     * @return 추가된 Tenant 정보
     */
    @PostMapping(value = "/selectedDetail", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Tenant 추가", description = "Tenant를 추가한다.")
    public CommonJsonResponse selectedDetailcreate(@RequestBody TenantDetail tenantDetail, CommonJsonResponse commonJsonResponse)
            throws DataDuplicateException, InvalidRequestException {

        tenantService.createDetail(tenantDetail);
        commonJsonResponse.setData(tenantDetail);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * Tenant 정보를 수정한다.
     *
     * @param tenantDetail 수정할 Tenant 정보
     * @return 수정된 Tenant 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/selectedDetail/update", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Tenant 수정", description = "해당 키의 Tenant를 수정한다")
    public CommonJsonResponse selectedDetailupdate(@RequestBody TenantDetail tenantDetail, CommonJsonResponse commonJsonResponse)
            throws DataNotFoundException, InvalidRequestException, DataDuplicateException {

        tenantService.selectedDetailupdate(tenantDetail);
        commonJsonResponse.setData(tenantDetail);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * Tenant 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 Tenant 목록
     */
    @GetMapping(value = "getTenantForUserList", produces = {"application/json", "application/xml"})
    @Operation(summary = "사용자 관리에서 Tenant 목록 조회", description = "Tenant 목록을 조회한다.")
    public CommonJsonResponse getTenantForUserList(TenantSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        condition.setSearchCoCd(CommonUtils.replacePercentAndUnderbar(condition.getSearchCoCd()));
        condition.setCoNm(CommonUtils.replacePercentAndUnderbar(condition.getCoNm()));
        condition.setUserId(CommonUtils.replacePercentAndUnderbar(condition.getUserId()));
        condition.setUserNm(CommonUtils.replacePercentAndUnderbar(condition.getUserNm()));

        commonJsonResponse.setData(tenantService.getTenantForUserList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }


    /**
     * Tenant 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 Tenant 목록
     */
    @GetMapping(value = "getTenantforMenuList", produces = {"application/json", "application/xml"})
    @Operation(summary = "사용자 관리에서 Tenant 목록 조회", description = "Tenant 목록을 조회한다.")
    public CommonJsonResponse getTenantforMenuList(TenantSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(tenantService.getTenantforMenuList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }
}
