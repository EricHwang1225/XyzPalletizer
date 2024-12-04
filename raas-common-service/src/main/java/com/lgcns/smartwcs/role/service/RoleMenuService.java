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

import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.role.model.RoleMenu;
import com.lgcns.smartwcs.role.model.RoleMenuSearchCondition;
import com.lgcns.smartwcs.role.repository.RoleMenuMappingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * Role Master 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class RoleMenuService {

    private final RoleMenuMappingMapper roleMenuMappingMapper;
    private final Validator validator;

    /**
     * Role별 메뉴 목록을 얻어온다.
     *
     * @param condition
     * @return
     */
    public List<RoleMenu> getUnpagedList(RoleMenuSearchCondition condition) {

        if (condition.getLangCd() == null)
            condition.setLangCd("ko");
        return roleMenuMappingMapper.getRoleMenuList(condition);
    }

    /**
     * Role별 메뉴 목록을 저장한다.
     *
     * @param roleMenu
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void merge(RoleMenu roleMenu) throws InvalidRequestException {
        this.validate(roleMenu);

        LocalDateTime now = LocalDateTime.now();
        roleMenu.setRegDt(now);

        roleMenuMappingMapper.saveRoleMenu(roleMenu);
    }

    private void validate(RoleMenu roleMenu) throws InvalidRequestException {
        Map<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "coCd", roleMenu.getCoCd(), "column.coCd", 30, messageArray);
        validator.validateAndThrow(true, "cntrCd", roleMenu.getCntrCd(), "column.cntrCd", 30, messageArray);
        validator.validateAndThrow(true, "roleCd", roleMenu.getRoleCd(), "column.roleCd", 30, messageArray);
        validator.validateAndThrow(true, "menuId", roleMenu.getMenuId(), "column.menuId", 30, messageArray);
        validator.validateAndThrow(true, "roleMenuCd", roleMenu.getRoleMenuCd(), "column.roleMenuCd", 30, messageArray);
        validator.validateAndThrow(true, "regId", roleMenu.getRegId(), "column.regId", 30, messageArray);
    }
}
