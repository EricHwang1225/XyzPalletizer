/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.multi_language.menu.service;

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.menu.model.Menu;
import com.lgcns.smartwcs.multi_language.menu.model.MultiLangMenu;
import com.lgcns.smartwcs.multi_language.menu.model.MultiLangMenuSearchCondition;
import com.lgcns.smartwcs.multi_language.menu.repository.MultiLangMenuMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * <PRE>
 * MultiLang Menu 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class MultiLangMenuService {

    private final MultiLangMenuMapper multiLangMenuMapper;
    private final Validator validator;

    /**
     * MultiLang Menu 목록을 조회한다.
     * @param condition 검색조건
     * @return 페이징된 MultiLang Menu 목록.
     */
    public List<Menu> getUnpagedList(MultiLangMenuSearchCondition condition) {

        return multiLangMenuMapper.getMultiLangMenuTreeList(condition);
    }


    /**
     * MultiLang에 Menu를 Mapping한다.
     * @param multiLangMenu 수정할 MultiLang Menu 정보
     * @return 수정된 MultiLang Menu 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void merge(MultiLangMenu multiLangMenu) throws InvalidRequestException {
        this.validate(multiLangMenu);

        LocalDateTime now = LocalDateTime.now();
        multiLangMenu.setRegDt(now);

        multiLangMenuMapper.saveMultiLangMenu(multiLangMenu);
    }

    private void validate(MultiLangMenu multiLangMenu) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "menuId", multiLangMenu.getMenuId(), "column.menuId", 30, messageArray);
        validator.validateAndThrow(true, "regId", multiLangMenu.getRegId(), "column.regId", 30, messageArray);
    }
}
