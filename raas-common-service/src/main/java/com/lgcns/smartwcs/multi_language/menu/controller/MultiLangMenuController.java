/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.multi_language.menu.controller;

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.multi_language.menu.model.MultiLangMenu;
import com.lgcns.smartwcs.multi_language.menu.model.MultiLangMenuSearchCondition;
import com.lgcns.smartwcs.multi_language.menu.service.MultiLangMenuService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * MultiLang 별 Menu 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/multiLang/menu")
public class MultiLangMenuController {

    /**
     * Multi Language 별 Menu Service 객체
     */
    @Autowired
    private MultiLangMenuService multiLangMenuService;

    /**
     * MultiLang 메뉴 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 MultiLang 메뉴 목록
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "MultiLang 메뉴 목록 조회", description = "MultiLang 메뉴 목록을 조회한다.")
    public CommonJsonResponse list(MultiLangMenuSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(multiLangMenuService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * MultiLang 메뉴 정보를 수정한다.
     *
     * @param multiLangMenu 수정할 MultiLang 메뉴 정보
     * @return 수정된 MultiLang 메뉴 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/update", produces = {"application/json"})
    @Operation(summary = "MultiLang 메뉴 Mapping", description = "MultiLang 메뉴 Mapping")
    public CommonJsonResponse merge(@RequestBody MultiLangMenu multiLangMenu, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException {

        multiLangMenuService.merge(multiLangMenu);
        commonJsonResponse.setData(multiLangMenu);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

}
