/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.wms_url.controller;

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonExcelModel;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.ExcelView;
import com.lgcns.smartwcs.wms_url.model.WmsUrl;
import com.lgcns.smartwcs.wms_url.model.WmsUrlDTO;
import com.lgcns.smartwcs.wms_url.model.WmsUrlSearchCondition;
import com.lgcns.smartwcs.wms_url.service.WmsUrlExcelService;
import com.lgcns.smartwcs.wms_url.service.WmsUrlService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * WmsUrl 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/wmsUrl")
public class WmsUrlController {

    /**
     * WmsUrl Service 객체
     */
    @Autowired
    private WmsUrlService wmsUrlService;

    @Autowired
    private WmsUrlExcelService wmsUrlExcelService;

    /**
     * 사용자 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 사용자 목록
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회한다.")
    public CommonJsonResponse list(WmsUrlSearchCondition condition, CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData(wmsUrlService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }


    /**
     * 사용자 정보를 수정한다.
     *
     * @param wmsUrlDTO 수정할 사용자 정보
     * @return 수정된 사용자 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/update", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "사용자 수정", description = "해당 키의 사용자를 수정한다")
    public CommonJsonResponse update(@RequestBody WmsUrlDTO wmsUrlDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataNotFoundException {
        WmsUrl wmsUrl = wmsUrlDTO.dtoMapping();

        wmsUrlService.merge(wmsUrl);
        commonJsonResponse.setData(wmsUrl);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }


    @GetMapping(value = "/excelDownload", produces = {"application/json"})
    public ExcelView excelTransform(
            WmsUrlSearchCondition condition
            , Map<String, Object> modelMap
            , HttpServletResponse response) {

        response.setHeader("Content-disposition", "attachment; filename=" + condition.getFileName() + ".xlsx"); //target명을 파일명으로 작성

        //엑셀에 작성할 리스트를 가져온다.
        List<CommonExcelModel> commonExcelModelList = wmsUrlExcelService.getWmsUrlExcel(condition);

        modelMap.put("modelList", commonExcelModelList);

        return new ExcelView();
    }

}
