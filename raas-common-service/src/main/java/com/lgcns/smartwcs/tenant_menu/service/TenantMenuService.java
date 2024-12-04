/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.tenant_menu.service;

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.menu.model.Menu;
import com.lgcns.smartwcs.tenant.model.TenantSearchCondition;
import com.lgcns.smartwcs.tenant_menu.model.TenantMenu;
import com.lgcns.smartwcs.tenant_menu.repository.TenantMenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * <PRE>
 * Tenant Menu 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class TenantMenuService {

    private final TenantMenuMapper tenantMenuMapper;
    private final Validator validator;

    /**
     * Tenant Menu 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 Tenant Menu 목록.
     */
    public List<Menu> getUnpagedList(TenantSearchCondition condition) {

        if (condition.getLangCd() == null)
            condition.setLangCd("ko");

        return tenantMenuMapper.getTenantMenuTreeList(condition);
    }


    /**
     * Tenant에 Menu를 Mapping한다.
     *
     * @param tenantMenu 수정할 Tenant Menu 정보
     * @return 수정된 Tenant Menu 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void merge(TenantMenu tenantMenu) throws InvalidRequestException {
        this.validate(tenantMenu);

        LocalDateTime now = LocalDateTime.now();
        tenantMenu.setRegDt(now);

        tenantMenuMapper.saveTenantMenu(tenantMenu);
    }

    private void validate(TenantMenu tenantMenu) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(false, "useYn", tenantMenu.getUseYn(), "column.useYn", 1, messageArray);
        validator.validateAndThrow(true, "regId", tenantMenu.getRegId(), "column.regId", 30, messageArray);
    }
}
