/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.multi_language.code.controller;

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonExcelModel;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.ExcelView;
import com.lgcns.smartwcs.multi_language.code.model.MultiLangCode;
import com.lgcns.smartwcs.multi_language.code.service.MultiLangCodeExcelService;
import com.lgcns.smartwcs.multi_language.code.service.MultiLangCodeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * MultiLang 별 Code 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/multiLang/code")
public class MultiLangCodeController {

    /**
     * Multi Language 별 Code Service 객체
     */
    @Autowired
    private MultiLangCodeService multiLangCodeService;

    @Autowired
    private MultiLangCodeExcelService multiLangCodeExcelService;

    /**
     * MultiLang 메뉴 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 MultiLang 메뉴 목록
     */
    @GetMapping(value = "/list", produces = {"application/json", "application/xml"})
    @Operation(summary = "MultiLang 메뉴 목록 조회", description = "MultiLang 메뉴 목록을 조회한다.")
    public CommonJsonResponse list(MultiLangCode condition, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(multiLangCodeService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * MultiLang 메뉴 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 MultiLang 메뉴 목록
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "MultiLang 메뉴 목록 조회", description = "MultiLang 메뉴 목록을 조회한다.")
    public CommonJsonResponse mergedList(MultiLangCode condition, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(multiLangCodeService.mergedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * MultiLang 메뉴 정보를 수정한다.
     *
     * @param multiLangCode 수정할 MultiLang 메뉴 정보
     * @return 수정된 MultiLang 메뉴 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/update", produces = {"application/json"})
    @Operation(summary = "MultiLang 메뉴 Mapping", description = "MultiLang 메뉴 Mapping")
    public CommonJsonResponse merge(@RequestBody MultiLangCode multiLangCode, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException {

        multiLangCodeService.merge(multiLangCode);
        commonJsonResponse.setData(multiLangCode);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @GetMapping(value = "/excelDownload", produces = {"application/json"})
    public ExcelView excelTransform(
            MultiLangCode condition
            , Map<String, Object> modelMap
            , HttpServletResponse response) {

        response.setHeader("Content-disposition", "attachment; filename=" + condition.getFileName() + ".xlsx"); //target명을 파일명으로 작성

        //엑셀에 작성할 리스트를 가져온다.
        List<CommonExcelModel> commonExcelModelList = multiLangCodeExcelService.getMultiLangCodeExcel(condition);

        modelMap.put("modelList", commonExcelModelList);

        return new ExcelView();
    }

}
