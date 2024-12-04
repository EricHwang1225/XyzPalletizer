/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.code.controller;

import com.lgcns.smartwcs.code.model.Code;
import com.lgcns.smartwcs.code.model.CodeDTO;
import com.lgcns.smartwcs.code.model.CodeSearchCondition;
import com.lgcns.smartwcs.code.service.CodeExcelService;
import com.lgcns.smartwcs.code.service.CodeService;
import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonExcelModel;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.ExcelView;
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
 * 공통 코드 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/code")
public class CodeController {

    /**
     * 공통코드 서비스 객체.
     */
    @Autowired
    private CodeService codeService;

    @Autowired
    private CodeExcelService codeExcelService;

    /**
     * 공통 코드 목록을 조회한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 공통코드 목록.
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "코드 목록 조회", description = "코드의 목록을 조회한다.")
    public CommonJsonResponse list(CodeSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(codeService.getUnPagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 공통 코드를 생성한다.
     *
     * @param codeDTO 생성할 공통코드 객체.
     * @return 저장된 공통코드 객체
     */
    @PostMapping(produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "코드 생성", description = "코드를 생성한다.")
    public CommonJsonResponse save(@RequestBody CodeDTO codeDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataDuplicateException {
        Code code = codeDTO.dtoMapping();

        commonJsonResponse.setData(codeService.create(code));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 공통 코드를 수정한다.
     *
     * @param codeDTO 수정할 공통 코드 객체
     * @return 업데이트 된 공통 코드 객체
     * @throws DataNotFoundException 해당 키의 공통 코드를 찾을 수 없을때 발생.
     */
    @PostMapping(value = "/update", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "코드 수정", description = "해당 키의 코드를 수정한다")
    public CommonJsonResponse update(@RequestBody CodeDTO codeDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataNotFoundException {
        Code code = codeDTO.dtoMapping();

        commonJsonResponse.setData(codeService.update(code));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }


    /**
     * 공통 코드를 삭제한다.
     *
     * @param codeDTO 삭제할 공통 코드 객체.
     */
    @PostMapping(value = "/delete", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "코드 삭제", description = "해당 키의 코드를 삭제한다.")
    public CommonJsonResponse delete(@RequestBody CodeDTO codeDTO, CommonJsonResponse commonJsonResponse) throws DataNotFoundException {
        Code code = codeDTO.dtoMapping();

        codeService.delete(code);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }


    @GetMapping(value = "/excelDownload", produces = {"application/json"})
    public ExcelView excelTransform(
            CodeSearchCondition condition
            , Map<String, Object> modelMap
            , HttpServletResponse response) {

        response.setHeader("Content-disposition", "attachment; filename=" + condition.getFileName() + ".xlsx"); //target명을 파일명으로 작성

        //엑셀에 작성할 리스트를 가져온다.
        List<CommonExcelModel> commonExcelModelList = codeExcelService.getAllCodeExcel(condition);

        modelMap.put("modelList", commonExcelModelList);

        return new ExcelView();
    }
}
