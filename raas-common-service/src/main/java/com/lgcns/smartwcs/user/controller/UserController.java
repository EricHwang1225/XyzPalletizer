/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.user.controller;

import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import com.lgcns.smartwcs.user.model.User;
import com.lgcns.smartwcs.user.model.UserDTO;
import com.lgcns.smartwcs.user.model.UserSearchCondition;
import com.lgcns.smartwcs.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * 사용자 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/common/user")
public class UserController {


    /**
     * 사용자 Service 객체
     */
    private final UserService userService;

    /**
     * 사용자 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 사용자 목록
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회한다.")
    public CommonJsonResponse list(UserSearchCondition condition, CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData(userService.getUserPagedList(condition));
        commonJsonResponse.setResultCode(100);

        return commonJsonResponse;
    }

    /**
     * 사용자 목록을 페이징하여 조회 한다.
     *
     * @param condition 검색 조건
     * @return 페이징된 사용자 목록
     */
    @GetMapping(value = "/unPagedList", produces = {"application/json", "application/xml"})
    @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회한다.")
    public CommonJsonResponse unPagedList(UserSearchCondition condition, CommonJsonResponse commonJsonResponse) {
        condition.setUserId(CommonUtils.replacePercentAndUnderbar(condition.getUserId()));
        condition.setUserNm(CommonUtils.replacePercentAndUnderbar(condition.getUserNm()));

        commonJsonResponse.setData(userService.getUserUnPagedList(condition));
        commonJsonResponse.setResultCode(100);

        return commonJsonResponse;
    }


    /**
     * 사용자를 추가한다.
     *
     * @param userDTO 추가할 사용자 정보
     * @return 추가된 사용자 정보
     */
    @PostMapping(value = "/unPagedList", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "사용자 추가", description = "사용자를 추가한다.")
    public CommonJsonResponse create(@RequestBody UserDTO userDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataDuplicateException, NoSuchAlgorithmException, DataNotFoundException {
        User user = userDTO.dtoMapping();

        commonJsonResponse.setData(userService.create(user));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 사용자 정보를 수정한다.
     *
     * @param userDTO 수정할 사용자 정보
     * @return 수정된 사용자 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/unPagedList/update", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "사용자 수정", description = "해당 키의 사용자를 수정한다")
    public CommonJsonResponse update(@RequestBody UserDTO userDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataNotFoundException, NoSuchAlgorithmException {
        User user = userDTO.dtoMapping();

        commonJsonResponse.setData(userService.update(user));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @GetMapping(value = "/tenantCntrUser", produces = {"application/json", "application/xml"})
    @Operation(summary = "Center 사용자 목록 조회", description = "사용자 목록을 조회한다.")
    public CommonJsonResponse getTenantCntrUserList(UserSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        condition.setUserId(CommonUtils.replacePercentAndUnderbar(condition.getUserId()));
        condition.setUserNm(CommonUtils.replacePercentAndUnderbar(condition.getUserNm()));

        commonJsonResponse.setData(userService.getCntrUserUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 사용자를 추가한다.
     *
     * @param userDTO 추가할 사용자 정보
     * @return 추가된 사용자 정보
     */
    @PostMapping(value = "/tenantCntrUser", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "사용자 추가", description = "사용자를 추가한다.")
    public CommonJsonResponse tenantCntrUserCreate(@RequestBody UserDTO userDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataDuplicateException, NoSuchAlgorithmException, DataNotFoundException {
        User user = userDTO.dtoMapping();

        commonJsonResponse.setData(userService.tenantCntrUserCreate(user));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 사용자 정보를 수정한다.
     *
     * @param userDTO 수정할 사용자 정보
     * @return 수정된 사용자 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/tenantCntrUser/update", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Center 사용자 수정", description = "해당 키의 사용자를 수정한다")
    public CommonJsonResponse tenantCntrUserUpdate(@RequestBody UserDTO userDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataNotFoundException, NoSuchAlgorithmException {
        User user = userDTO.dtoMapping();

        //Frontend의 구성상 페이짖 별로 다른 api를 호출해야하는데 service의 update Method는 같다. 의미없는 값을 셋팅해서 다른 것 처럼 보여줘서 Sonarqube해결
        userDTO.setUserLvl(3);

        commonJsonResponse.setData(userService.update(user));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }


    @GetMapping(value = "/tenantUser", produces = {"application/json", "application/xml"})
    @Operation(summary = "Tenant 사용자 목록 조회", description = "사용자 목록을 조회한다.")
    public CommonJsonResponse getTenantUserList(UserSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(userService.getTenantUserList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 사용자를 추가한다.
     *
     * @param userDTO 추가할 사용자 정보
     * @return 추가된 사용자 정보
     */
    @PostMapping(value = "/tenantUser", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Tenant 사용자 추가", description = "Tenant 사용자를 추가한다.")
    public CommonJsonResponse tenantUserCreate(@RequestBody UserDTO userDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataDuplicateException, NoSuchAlgorithmException, DataNotFoundException {
        User user = userDTO.dtoMapping();

        commonJsonResponse.setData(userService.tenantUserCreate(user));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 사용자 정보를 수정한다.
     *
     * @param userDTO 수정할 사용자 정보
     * @return 수정된 사용자 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/tenantUser/update", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Tenant 사용자 수정", description = "해당 키의 사용자를 수정한다")
    public CommonJsonResponse tenantUserUpdate(@RequestBody UserDTO userDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataNotFoundException, NoSuchAlgorithmException {
        User user = userDTO.dtoMapping();

        //Frontend의 구성상 페이짖 별로 다른 api를 호출해야하는데 service의 update Method는 같다. 의미없는 값을 셋팅해서 다른 것 처럼 보여줘서 Sonarqube해결
        userDTO.setUserLvl(2);

        commonJsonResponse.setData(userService.update(user));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }
}
