package com.lgcns.smartwcs.role.controller;

import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.role.model.Role;
import com.lgcns.smartwcs.role.model.RoleDTO;
import com.lgcns.smartwcs.role.model.RoleSearchCondition;
import com.lgcns.smartwcs.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * 권한 도메인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/common/role")
public class RoleController {

    /**
     * 권한 Service 객체
     */
    @Autowired
    private RoleService roleService;

    /**
     * 권한 목록 전체를 조회한다.
     *
     * @return 권한 목록
     */
    @GetMapping(value = "codes", produces = {"application/json", "application/xml"})
    @Operation(summary = "권한 목록 조회", description = "권한 목록을 조회한다.")
    public Iterable<Role> list(RoleSearchCondition condition) {
        return roleService.getList(condition);
    }

    /**
     * Role Master 목록을 조회한다.
     * //* @param pageable 페이징 객체
     *
     * @param condition 검색 조건
     * @return 페이징된 Sku Master 목록.
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "Role 리스트 조회", description = "Role 리스트를 조회한다. ")
    public CommonJsonResponse list(RoleSearchCondition condition, Pageable pageable, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(roleService.getList(condition, pageable));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * Role Master 목록을 조회한다.
     * //* @param pageable 페이징 객체
     *
     * @param condition 검색 조건
     * @return 페이징된 Sku Master 목록.
     */
    @GetMapping(value = "/getUnpagedList", produces = {"application/json", "application/xml"})
    @Operation(summary = "Role 리스트 조회", description = "Role 리스트를 조회한다. ")
    public CommonJsonResponse getUnpagedList(RoleSearchCondition condition, CommonJsonResponse commonJsonResponse) {

        commonJsonResponse.setData(roleService.getList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 권한을 추가한다.
     *
     * @param roleDTO 추가할 권한 정보
     * @return 추가한 권한 정보.
     */
    @PostMapping(value = "/getUnpagedList", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "권한 생성", description = "권한을 생성한다.")
    public CommonJsonResponse save(@RequestBody RoleDTO roleDTO, CommonJsonResponse commonJsonResponse)
            throws DataDuplicateException, InvalidRequestException {
        Role role = roleDTO.dtoMapping();

        commonJsonResponse.setData(roleService.create(role));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    /**
     * 권한 정보를 수정한다.
     *
     * @param roleDTO 수정할 권한 정보
     * @return 수정된 권한 정보
     * @throws DataNotFoundException
     */
    @PostMapping(value = "/getUnpagedList/update", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "권한 수정", description = "해당 키의 권한을 수정한다")
    public CommonJsonResponse update(@RequestBody RoleDTO roleDTO, CommonJsonResponse commonJsonResponse)
            throws DataNotFoundException, InvalidRequestException {
        Role role = roleDTO.dtoMapping();

        commonJsonResponse.setData(roleService.update(role));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }


}
