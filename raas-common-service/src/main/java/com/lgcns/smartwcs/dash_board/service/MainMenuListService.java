/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.dash_board.service;

import com.lgcns.smartwcs.dash_board.model.MainMenuList;
import com.lgcns.smartwcs.dash_board.repository.MainMenuListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 화면별 작업량 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
public class MainMenuListService {

    /**
     * 작업량  Mapper
     */
    @Autowired
    private MainMenuListMapper mainMenuListMapper;
    /**
     * 각 화면별 작업량을 조회 한다.
     * @param mainMenuList 검색 조건
     * @return 각 화면별 작업량
     */

    public List<Map<String, Object>> getMenuList(MainMenuList mainMenuList) {
        return mainMenuListMapper.getMenuList(mainMenuList);
    }
}
