/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.dash_board.controller;

import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.dash_board.model.MainMenuList;
import com.lgcns.smartwcs.dash_board.model.Workload;
import com.lgcns.smartwcs.dash_board.service.MainMenuListService;
import com.lgcns.smartwcs.dash_board.service.WorkloadService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * 입고 오더 처리를 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j

@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardController {

    /**
     * DashBoard의 작업량 가져오는 Service 객체
     */
    @Autowired
    private WorkloadService workloadService;

    @Autowired
    private MainMenuListService mainMenuListService;

    /**
     * 각 화면별 작업량을 조회 한다.
     *
     * @param workload 검색 조건
     * @return 각 화면별 작업량
     */
    @PostMapping(value = "/workload", produces = {"application/json"})
    @Operation(summary = "화면 별 작업량을 조회", description = "화면 별 작업량 조회")
    public CommonJsonResponse workload(@RequestBody Workload workload, CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData(workloadService.workload(workload));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 각 화면별 작업량을 조회 한다.
     *
     * @param mainMenuList 검색 조건
     * @return 각 화면별 작업량
     */
    @PostMapping(value = "/getMenuList", produces = {"application/json"})
    @Operation(summary = "메인화면 메뉴 리스트 조회", description = "메인화면 메뉴 리스트 조회")
    public CommonJsonResponse menuList(@RequestBody MainMenuList mainMenuList, CommonJsonResponse commonJsonResponse) {
        mainMenuList.setLangCd(LocaleContextHolder.getLocale().getLanguage());

        commonJsonResponse.setData(mainMenuListService.getMenuList(mainMenuList));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }
}
