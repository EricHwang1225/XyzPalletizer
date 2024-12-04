/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.tenant_menu.controller;

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.tenant.model.TenantSearchCondition;
import com.lgcns.smartwcs.tenant_menu.model.TenantMenu;
import com.lgcns.smartwcs.tenant_menu.service.TenantMenuService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Tenant 별 Menu 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/tenant/menu")
public class TenantMenuController {

    /**
     * Tenant 별 Menu Service 객체
     */
    @Autowired
    private TenantMenuService tenantMenuService;

    /**
     * Tenant 메뉴 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 Tenant 메뉴 목록
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "Tenant 메뉴 목록 조회", description = "Tenant 메뉴 목록을 조회한다.")
    public CommonJsonResponse list(TenantSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(tenantMenuService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * Tenant 메뉴 정보를 수정한다.
     *
     * @param tenantMenu 수정할 Tenant 메뉴 정보
     * @return 수정된 Tenant 메뉴 정보
     * @throws DataNotFoundException
     */
    @PostMapping(produces = {"application/json"})
    @Operation(summary = "Tenant 메뉴 Mapping", description = "Tenant 메뉴 Mapping")
    public CommonJsonResponse merge(@RequestBody TenantMenu tenantMenu, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException {

        tenantMenuService.merge(tenantMenu);
        commonJsonResponse.setData(tenantMenu);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

}
