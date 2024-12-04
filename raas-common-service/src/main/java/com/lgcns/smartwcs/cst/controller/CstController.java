/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.cst.controller;


import com.lgcns.smartwcs.common.model.CommonExcelModel;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.ExcelView;
import com.lgcns.smartwcs.cst.model.CstSearchCondition;
import com.lgcns.smartwcs.cst.service.CstExcelService;
import com.lgcns.smartwcs.cst.service.CstService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Cst 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/cst")
public class CstController {

    /**
     * Cst Service 객체
     */
    @Autowired
    private CstService cstService;

    @Autowired
    private CstExcelService cstExcelService;

    /**
     * 사용자 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 사용자 목록
     */
    @GetMapping(value = "/getUnpagedList", produces = {"application/json"})
    @Operation(summary = "공통 코드 목록 조회", description = "공통 코드 목록을 조회한다.")
    public CommonJsonResponse list(CstSearchCondition condition, CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData(cstService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "공통 코드 목록 조회", description = "공통 코드 목록을 조회한다.")
    public CommonJsonResponse list(CstSearchCondition condition, Pageable pageable, CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData(cstService.getList(condition, pageable));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @GetMapping(value = "/excelDownload", produces = {"application/json"})
    public ExcelView excelTransform(
            CstSearchCondition condition
            , Map<String, Object> modelMap
            , HttpServletResponse response) {

        response.setHeader("Content-disposition", "attachment; filename=" + condition.getFileName() + ".xlsx"); //target명을 파일명으로 작성

        //엑셀에 작성할 리스트를 가져온다.
        List<CommonExcelModel> commonExcelModelList = cstExcelService.getCstExcel(condition);

        modelMap.put("modelList", commonExcelModelList);

        return new ExcelView();
    }

}
