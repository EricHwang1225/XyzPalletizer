/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.port.controller;

import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.port.model.Port;
import com.lgcns.smartwcs.port.model.PortDTO;
import com.lgcns.smartwcs.port.model.PortSearchCondition;
import com.lgcns.smartwcs.port.model.ids.PortPkId;
import com.lgcns.smartwcs.port.service.PortService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Port 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/port")
public class PortController {

    /**
     * Port Service 객체
     */
    @Autowired
    private PortService portService;

    /**
     * Port 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 사용자 목록
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회한다.")
    public CommonJsonResponse list(PortSearchCondition condition, Pageable pageable, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(portService.getList(condition, pageable));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * Port 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 사용자 목록
     */
    @GetMapping(value = "/getUnpagedList", produces = {"application/json", "application/xml"})
    @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회한다.")
    public CommonJsonResponse getUnpagedList(PortSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(portService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * Port 목록을 페이징하여 조회 한다.
     *
     * @param id 검색 조건
     * @return 페이징된 사용자 목록
     */
    @GetMapping(value = "/id", produces = {"application/json", "application/xml"})
    @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회한다.")
    public CommonJsonResponse get(PortPkId id, CommonJsonResponse commonJsonResponse) throws DataNotFoundException {

        commonJsonResponse.setData(portService.get(id));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }


    /**
     * 사용자를 추가한다.
     *
     * @param portDTO 추가할 사용자 정보
     * @return 추가된 사용자 정보
     */
    @PostMapping(value = "/getUnpagedList", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "사용자 추가", description = "사용자를 추가한다.")
    public CommonJsonResponse create(@RequestBody PortDTO portDTO, CommonJsonResponse commonJsonResponse)
            throws DataDuplicateException, InvalidRequestException {
        Port port = portDTO.dtoMapping();

        commonJsonResponse.setData(portService.create(port));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 사용자 정보를 수정한다.
     *
     * @param portDTO 수정할 사용자 정보
     * @return 수정된 사용자 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/getUnpagedList/update", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "사용자 수정", description = "해당 키의 사용자를 수정한다")
    public CommonJsonResponse update(@RequestBody PortDTO portDTO, CommonJsonResponse commonJsonResponse)
            throws DataNotFoundException, InvalidRequestException {
        Port port = portDTO.dtoMapping();

        commonJsonResponse.setData(portService.update(port));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

}
