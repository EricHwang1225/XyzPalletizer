/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.menu.controller;

import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import com.lgcns.smartwcs.menu.model.Menu;
import com.lgcns.smartwcs.menu.model.MenuDTO;
import com.lgcns.smartwcs.menu.model.MenuSearchCondition;
import com.lgcns.smartwcs.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Menu 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/menu")
public class MenuController {

    /**
     * Menu Service 객체
     */
    @Autowired
    private MenuService menuService;

    /**
     * 메뉴 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 메뉴 목록
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "메뉴 목록 조회", description = "메뉴 목록을 조회한다.")
    public CommonJsonResponse list(MenuSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        condition.setMenuId(CommonUtils.replacePercentAndUnderbar(condition.getMenuId()));
        condition.setMenuNm(CommonUtils.replacePercentAndUnderbar(condition.getMenuNm()));

        commonJsonResponse.setData(menuService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 메뉴를 추가한다.
     *
     * @param menuDTO 추가할 메뉴 정보
     * @return 추가된 메뉴 정보
     */
    @PostMapping(produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "메뉴 추가", description = "메뉴를 추가한다.")
    public CommonJsonResponse create(@RequestBody MenuDTO menuDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataDuplicateException {
        Menu menu = menuDTO.dtoMapping();
        commonJsonResponse.setData(menuService.create(menu));
        if (menu.getMenuLvl() == 2) {
            menuService.updateParent(menuService.updateParent(menu));
        }
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 메뉴 정보를 수정한다.
     *
     * @param menuDTO 수정할 메뉴 정보
     * @return 수정된 메뉴 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/update", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "메뉴 수정", description = "해당 키의 메뉴를 수정한다")
    public CommonJsonResponse update(@RequestBody MenuDTO menuDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataNotFoundException {
        Menu menu = menuDTO.dtoMapping();
        commonJsonResponse.setData(menuService.update(menu));
        if (menu.getMenuLvl() == 2) {
            menuService.updateParent(menuService.updateParent(menu));
        }
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

}
