/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.role.controller;

import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.role.model.RoleMenu;
import com.lgcns.smartwcs.role.model.RoleMenuSearchCondition;
import com.lgcns.smartwcs.role.service.RoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Sku Master 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */


@RestController
@RequestMapping(value = "/api/common/roleMenu")
public class RoleMenuController {

    /**
     * Sku Master 서비스 객체.
     */
    @Autowired
    private RoleMenuService roleMenuService;


    /**
     * Role별 Menu 목록을 조회한다.
     *
     * @return 메뉴 목록
     */
    @GetMapping(produces = {"application/json"})
    @Operation(summary = "Role 별 메뉴 목록 조회", description = "Role 별 메뉴 목록을 조회한다.")
    public CommonJsonResponse list(RoleMenuSearchCondition condition, CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData(roleMenuService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * Role과 Menu를 Mapping한다. 추가한다.
     *
     * @param roleMenu 추가할 메뉴 정보
     * @return 추가한 메뉴 정보
     */
    @PostMapping(produces = {"application/json"})
    @Operation(summary = "Role Menu Mapping ", description = "Role Menu 를 Mapping한다.")
    public CommonJsonResponse merge(@RequestBody RoleMenu roleMenu, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException {

        roleMenuService.merge(roleMenu);
        commonJsonResponse.setData(roleMenu);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }
}
