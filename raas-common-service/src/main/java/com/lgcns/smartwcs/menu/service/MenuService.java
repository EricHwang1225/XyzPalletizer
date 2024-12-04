/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.menu.service;

import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.menu.model.Menu;
import com.lgcns.smartwcs.menu.model.MenuSearchCondition;
import com.lgcns.smartwcs.menu.repository.MenuMapper;
import com.lgcns.smartwcs.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Menu 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final Validator validator;
    private final MessageSourceAccessor accessor;

    /**
     * Menu 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 Menu 목록.
     */
    public List<Menu> getUnpagedList(MenuSearchCondition condition) {
        return menuMapper.getMenuUnpagedList(condition);
    }

    /**
     * Menu를 추가한다.
     *
     * @param menu Menu 정보
     * @return 추가된 Menu 정보
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public Menu create(Menu menu) throws DataDuplicateException, InvalidRequestException {
        this.validate(menu);

        if (menuRepository.existsById(menu.getMenuId())) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(MessageEnum.MENU_ID.getValue(), "메뉴 ID : " + menu.getMenuId() + "가 존재합니다.");
            throw new DataDuplicateException(messageArray);
        }

        LocalDateTime now = LocalDateTime.now();

        menu.setRegDt(now);
        menu.setUpdDt(now);
        menu.setUpdId(menu.getRegId());

        return menuRepository.save(menu);
    }

    /**
     * Menu를 수정한다.
     *
     * @param menu 수정할 Menu 정보
     * @return 수정된 Menu 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public Menu update(Menu menu) throws DataNotFoundException, InvalidRequestException {
        this.validate(menu);

        final Optional<Menu> byId = menuRepository.findById(menu.getMenuId());
        if (!byId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(MessageEnum.MENU_ID.getValue(), "Menu ID : " + menu.getMenuId() + "를 찾을 수 없습니다.");
            throw new DataNotFoundException(messageArray);
        }

        Menu prevMenu = byId.get();

        // 변경가능한 항목만 변경
        prevMenu.setMenuNm(menu.getMenuNm());
        prevMenu.setMenuDesc(menu.getMenuDesc());
        prevMenu.setMenuLvl(menu.getMenuLvl());
        prevMenu.setMenuType(menu.getMenuType());
        prevMenu.setMenuUrl(menu.getMenuUrl());
        prevMenu.setMenuIcon(menu.getMenuIcon());
        prevMenu.setSortSeq(menu.getSortSeq());
        prevMenu.setUserLvl(menu.getUserLvl());
        prevMenu.setUseYn(menu.getUseYn());
        prevMenu.setMainShowIcon(menu.getMainShowIcon());

        LocalDateTime now = LocalDateTime.now();
        prevMenu.setUpdDt(now);
        prevMenu.setUpdId(menu.getRegId());

        return menuRepository.save(prevMenu);
    }


    public Menu updateParent(Menu menu) {

        List<Integer> userLvlList = menuMapper.getMenuUserLvlList(menu.getParentId());

        Integer parentUserLvl = 0;

        parentUserLvl = getParentUserLvl(userLvlList);

        menu = menuMapper.getByTreeId(menu.getParentId());
        menu.setUserLvl(parentUserLvl);

        menuRepository.save(menu);

        return menu;
    }

    public void validate(Menu menu) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "menuId", menu.getMenuId(), "column.menuId", 30, messageArray);
        validator.validateAndThrow(true, "menuNm", menu.getMenuNm(), "column.menuNm", 50, messageArray);
        validator.validateAndThrow(false, "menuDesc", menu.getMenuDesc(), "column.menuDesc", 1000, messageArray);
        validator.validateAndThrow(false, "menuType", menu.getMenuType(), "column.menuType", 20, messageArray);
        validator.validateAndThrow(true, "menuUrl", menu.getMenuUrl(), "column.menuUrl", 300, messageArray);
        validator.validateAndThrow(true, "treeId", menu.getTreeId(), "column.treeId", 30, messageArray);
        if (!StringUtils.hasText(menu.getParentId())) {
            if (menu.getMenuLvl() != 0) {
                messageArray.put("parentId", accessor.getMessage("column.parentId") + " " + accessor.getMessage("validation.notFound"));
                throw new InvalidRequestException(messageArray);
            }
        } else if (menu.getParentId().length() > 30) {
            messageArray.put("parentId", accessor.getMessage("column.parentId") + " " + accessor.getMessage("validation.subject") + " 30 " + accessor.getMessage("validation.maxLength"));
            throw new InvalidRequestException(messageArray);
        }
        validator.validateAndThrow(true, "useYn", menu.getUseYn(), "column.useYn", 1, messageArray);
        validator.validateAndThrow(false, "menuIcon", menu.getMenuIcon(), "column.menuIcon", 100, messageArray);

        validator.validateAndThrow(false, "sortSeq", menu.getSortSeq(), "column.sortSeq", 9, messageArray);
        validator.validateAndThrow(true, "menuLvl", menu.getMenuLvl(), "column.menuLvl", 9, messageArray);
        validator.validateAndThrow(false, "userLvl", menu.getUserLvl(), "column.userLvl", 9, messageArray);
    }

    private Integer getParentUserLvl(List<Integer> userLvlList) {
        Integer parentUserLvl;
        switch (userLvlList.get(0)) {
            case 7:
                parentUserLvl = 7;
                break;
            case 6:
                parentUserLvl = 6;
                if (userLvlList.contains(5) || userLvlList.contains(4) || userLvlList.contains(1)) {
                    parentUserLvl = 7;
                }
                break;
            case 5:
                parentUserLvl = 5;
                if (userLvlList.contains(4) || userLvlList.contains(2)) {
                    parentUserLvl = 7;
                }
                break;
            case 4:
                parentUserLvl = 4;
                if (userLvlList.contains(3)) {
                    parentUserLvl = 7;
                }
                break;
            case 3:
                parentUserLvl = getParentUserLvlForLevel3(userLvlList);
                break;
            case 2:
                parentUserLvl = 2;
                if (userLvlList.contains(1))
                    parentUserLvl = 4;
                break;
            case 1:
                parentUserLvl = 1;
                break;
            default:
                parentUserLvl = 0;
                break;
        }

        return parentUserLvl;
    }

    private Integer getParentUserLvlForLevel3(List<Integer> userLvlList) {
        Integer parentUserLvl = 3;
        if (userLvlList.contains(2)) {
            if (userLvlList.contains(1))
                parentUserLvl = 7;
            else
                parentUserLvl = 6;
        }

        return parentUserLvl;
    }
}
