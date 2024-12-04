/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.role.service;

import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.role.model.Role;
import com.lgcns.smartwcs.role.model.RoleSearchCondition;
import com.lgcns.smartwcs.role.model.ids.RoleId;
import com.lgcns.smartwcs.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * <PRE>
 * 권한을 조작하기 위한 Service 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private static final String ROLE_CD = "roleCd";
    private static final String LANG_ROLE_CD = "column.roleCd";
    private final RoleRepository roleRepository;
    private final Validator validator;
    private final MessageSourceAccessor accessor;

    /**
     * 권한 전체 목록을 얻어온다.
     *
     * @return
     */
    public Iterable<Role> getList(RoleSearchCondition condition) {
        return roleRepository.findAllBySearch(condition);
    }

    /**
     * Role Master 목록을 얻어온다.
     *
     * @param condition 검색 조건 객체
     * @param pageable  페이징 정보 객체
     * @return 페이징된 Role Master 목록
     */
    public Page<Role> getList(RoleSearchCondition condition, Pageable pageable) {

        return roleRepository.findAllBySearch(condition, pageable);
    }

    /**
     * Role Master 목록을 얻어온다.
     *
     * @param condition 검색 조건 객체
     * @return 페이징된 Role Master 목록
     */
    public List<Role> getUnpagedList(RoleSearchCondition condition) {
        return roleRepository.findAllBySearch(condition);
    }

    /**
     * 권한을 추가한다.
     *
     * @param role 추가할 권한 정보
     * @return 추가된 권한 객체
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public Role create(Role role) throws DataDuplicateException, InvalidRequestException {
        this.validate(role);

        RoleId id = RoleId.builder().coCd(role.getCoCd())
                .cntrCd(role.getCntrCd()).roleCd(role.getRoleCd()).build();

        if (roleRepository.existsById(id)) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(ROLE_CD, accessor.getMessage(LANG_ROLE_CD) + " " + role.getRoleCd() + " " + accessor.getMessage("validation.exist"));
            throw new DataDuplicateException(messageArray);
        }

        LocalDateTime now = LocalDateTime.now();

        role.setRegDt(now);
        role.setUpdDt(now);
        role.setUpdId(role.getRegId());

        return roleRepository.save(role);
    }

    /**
     * 권한을 수정한다.
     *
     * @param role 수정할 권한 정보
     * @return 수정된 권한 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public Role update(Role role) throws DataNotFoundException, InvalidRequestException {
        this.validate(role);

        RoleId id = RoleId.builder().coCd(role.getCoCd())
                .cntrCd(role.getCntrCd()).roleCd(role.getRoleCd()).build();

        final Optional<Role> byId = roleRepository.findById(id);
        if (!byId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(ROLE_CD, accessor.getMessage(LANG_ROLE_CD) + " " + role.getRoleCd() + " " + accessor.getMessage("validation.notFound"));
            throw new DataNotFoundException(messageArray);
        }

        Role prevRole = byId.get();

        // 변경가능한 항목만 변경
        prevRole.setRoleNm(role.getRoleNm());
        prevRole.setUseYn(role.getUseYn());

        LocalDateTime now = LocalDateTime.now();
        prevRole.setUpdDt(now);
        prevRole.setUpdId(role.getRegId());

        return roleRepository.save(prevRole);
    }

    private void validate(Role role) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "coCd", role.getCoCd(), "column.coCd", 30, messageArray);
        validator.validateAndThrow(true, "cntrCd", role.getCntrCd(), "column.cntrCd", 30, messageArray);
        validator.validateAndThrow(true, ROLE_CD, role.getRoleCd(), LANG_ROLE_CD, 30, messageArray);
        validator.validateAndThrow(true, "roleNm", role.getRoleNm(), "column.roleNm", 50, messageArray);
        validator.validateAndThrow(true, "useYn", role.getUseYn(), "column.useYn", 1, messageArray);
        validator.validateAndThrow(true, "regId", role.getRegId(), "column.regId", 30, messageArray);
    }
}
