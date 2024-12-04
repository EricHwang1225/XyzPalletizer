/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.eqp.controller;


import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.eqp.model.Eqp;
import com.lgcns.smartwcs.eqp.model.EqpDTO;
import com.lgcns.smartwcs.eqp.model.EqpSearchCondition;
import com.lgcns.smartwcs.eqp.service.EqpService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Eqp 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/eqp")
public class EqpController {

    /**
     * Eqp Service 객체
     */
    @Autowired
    private EqpService eqpService;

    /**
     * 사용자 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 사용자 목록
     */
    @GetMapping(value = {"", "/tote"}, produces = {"application/json", "application/xml"})
    @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회한다.")
    public CommonJsonResponse list(EqpSearchCondition condition, CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData(eqpService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 사용자 정보를 수정한다.
     *
     * @param eqpDTO 수정할 사용자 정보
     * @return 수정된 사용자 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/tote/update", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "사용자 수정", description = "해당 키의 사용자를 수정한다")
    public CommonJsonResponse update(@RequestBody EqpDTO eqpDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataNotFoundException {

        Eqp eqp = eqpDTO.dtoMapping();

        commonJsonResponse.setData(eqpService.updateTote(eqp));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

}
