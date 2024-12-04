/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.menu_uri_role.service;

import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.menu.model.Menu;
import com.lgcns.smartwcs.menu_uri_role.model.MenuUriRole;
import com.lgcns.smartwcs.menu_uri_role.model.MenuUriRoleSearchCondition;
import com.lgcns.smartwcs.menu_uri_role.repository.MenuUriRoleMapper;
import com.lgcns.smartwcs.menu_uri_role.repository.MenuUriRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
public class MenuUriRoleService {

    private static final String LANG_URI = "column.uri";
    private final MenuUriRoleRepository menuUriRoleRepository;
    private final MenuUriRoleMapper menuUriRoleMapper;
    private final Validator validator;
    private final MessageSourceAccessor accessor;

    /**
     * Menu 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 Menu 목록.
     */
    public List<Menu> getMenuUnpagedList(MenuUriRoleSearchCondition condition) {

        return menuUriRoleMapper.getMenuUriRoleUnpagedList(condition);
    }

    public List<MenuUriRole> getUnpagedList(MenuUriRoleSearchCondition condition) {

        return menuUriRoleRepository.findAllBySearch(condition);
    }

    /**
     * MenuUriRole 추가한다.
     *
     * @param menuUriRole MenuUriRole 정보
     * @return 추가된 MenuUriRole 정보
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public MenuUriRole create(MenuUriRole menuUriRole) throws DataDuplicateException, InvalidRequestException {
        this.validate(menuUriRole);

        MenuUriRoleSearchCondition menuUriRoleSearchCondition = MenuUriRoleSearchCondition.builder()
                .menuId(menuUriRole.getMenuId())
                .methodType(menuUriRole.getMethodType())
                .uri(menuUriRole.getUri()).build();
        List<MenuUriRole> menuUriRoleList = menuUriRoleRepository.checkUniqueKey(menuUriRoleSearchCondition);
        if (!menuUriRoleList.isEmpty()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put("uri", accessor.getMessage(LANG_URI) + " " + menuUriRole.getUri() + " " + accessor.getMessage("validation.exist"));
            throw new DataDuplicateException(messageArray);
        }

        LocalDateTime now = LocalDateTime.now();

        menuUriRole.setRegDt(now);
        menuUriRole.setUpdDt(now);
        menuUriRole.setUpdId(menuUriRole.getRegId());

        return menuUriRoleRepository.save(menuUriRole);
    }

    /**
     * Menu를 수정한다.
     *
     * @param menuUriRole 수정할 Menu 정보
     * @return 수정된 Menu 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public MenuUriRole update(MenuUriRole menuUriRole)
            throws DataNotFoundException, DataDuplicateException, InvalidRequestException {
        this.validate(menuUriRole);

        final Optional<MenuUriRole> byId = menuUriRoleRepository.findById(menuUriRole.getMethodUidKey());
        if (!byId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put("uri", accessor.getMessage(LANG_URI) + " " + menuUriRole.getMenuId() + " " + accessor.getMessage("validation.notFound"));
            throw new DataNotFoundException(messageArray);
        }

        MenuUriRoleSearchCondition menuUriRoleSearchCondition = MenuUriRoleSearchCondition.builder()
                .menuId(menuUriRole.getMenuId())
                .methodType(menuUriRole.getMethodType())
                .uri(menuUriRole.getUri())
                .build();

        List<MenuUriRole> menuUriRoleList = menuUriRoleRepository.checkUniqueKey(menuUriRoleSearchCondition);

        if (!menuUriRoleList.isEmpty() && !menuUriRoleList.get(0).getMethodUidKey().equals(menuUriRole.getMethodUidKey())) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put("uri", accessor.getMessage(LANG_URI) + " " + menuUriRole.getUri() + " " + accessor.getMessage("validation.exist"));
            throw new DataDuplicateException(messageArray);
        }

        MenuUriRole prevMenu = byId.get();

        // 변경가능한 항목만 변경
        prevMenu.setMethodType(menuUriRole.getMethodType());
        prevMenu.setUri(menuUriRole.getUri().length() > 1000 ? menuUriRole.getUri().substring(0, 999) : menuUriRole.getUri());
        prevMenu.setUriDesc(menuUriRole.getUriDesc());
        prevMenu.setUseYn(menuUriRole.getUseYn());

        LocalDateTime now = LocalDateTime.now();
        prevMenu.setUpdDt(now);
        prevMenu.setUpdId(menuUriRole.getRegId());

        return menuUriRoleRepository.save(prevMenu);
    }

    /**
     * Menu를 삭제한다.
     *
     * @param menuUriRole 삭제할 Menu 정보
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void delete(MenuUriRole menuUriRole) throws DataNotFoundException {

        if (!menuUriRoleRepository.findById(menuUriRole.getMethodUidKey()).isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put("uri", accessor.getMessage(LANG_URI) + " " + menuUriRole.getMenuId() + " " + accessor.getMessage("validation.notFound"));
            throw new DataNotFoundException(messageArray);
        }

        menuUriRoleRepository.deleteById(menuUriRole.getMethodUidKey());
    }

    /**
     * MenuDTO 객체 검증
     *
     * @throws InvalidRequestException
     */
    private void validate(MenuUriRole menuUriRole) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "menuId", menuUriRole.getMenuId(), "column.menuId", 30, messageArray);
        validator.validateAndThrow(true, "methodType", menuUriRole.getMethodType(), "column.methodType", 6, messageArray);
        validator.validateAndThrow(true, "uri", menuUriRole.getUri(), LANG_URI, 100, messageArray);
        validator.validateAndThrow(false, "uriDesc", menuUriRole.getUriDesc(), "column.uriDesc", 300, messageArray);
        validator.validateAndThrow(true, "useYn", menuUriRole.getUseYn(), "column.useYn", 1, messageArray);
    }
}
