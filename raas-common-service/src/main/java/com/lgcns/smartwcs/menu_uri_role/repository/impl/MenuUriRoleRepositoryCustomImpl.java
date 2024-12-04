/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.menu_uri_role.repository.impl;

import com.lgcns.smartwcs.menu_uri_role.model.MenuUriRole;
import com.lgcns.smartwcs.menu_uri_role.model.MenuUriRoleSearchCondition;
import com.lgcns.smartwcs.menu_uri_role.model.QMenuUriRole;
import com.lgcns.smartwcs.menu_uri_role.repository.MenuUriRoleRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <PRE>
 * MenuUriRole 검색용 QueryDSL Custom 구현 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
public class MenuUriRoleRepositoryCustomImpl implements MenuUriRoleRepositoryCustom {

    /**
     * JPA 쿼리용 객체.
     */
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MenuUriRole> findAllBySearch(MenuUriRoleSearchCondition condition) {
        return jpaQueryFactory
                .selectFrom(QMenuUriRole.menuUriRole)
                .where(
                        ctMenuId(condition.getMenuId()),
                        ctUri(condition.getUri()),
                        eqUseYn(condition.getUseYn())
                ).fetch();
    }

    @Override
    public List<MenuUriRole> checkUniqueKey(MenuUriRoleSearchCondition condition) {
        return jpaQueryFactory
                .selectFrom(QMenuUriRole.menuUriRole)
                .where(
                        eqMenuId(condition.getMenuId()),
                        eqMethodType(condition.getMethodType()),
                        eqUri(condition.getUri()),
                        eqUseYn(condition.getUseYn())
                ).fetch();
    }

    private BooleanExpression ctMenuId(String menuId) {
        return menuId == null ? null : QMenuUriRole.menuUriRole.menuId.contains(menuId);
    }

    private BooleanExpression ctUri(String uri) {
        return uri == null ? null : QMenuUriRole.menuUriRole.uri.contains(uri);
    }

    private BooleanExpression eqUseYn(String useYn) {
        return useYn == null ? null : QMenuUriRole.menuUriRole.useYn.eq(useYn);
    }

    private BooleanExpression eqMenuId(String menuId) {
        return menuId == null ? null : QMenuUriRole.menuUriRole.menuId.eq(menuId);
    }

    private BooleanExpression eqUri(String uri) {
        return uri == null ? null : QMenuUriRole.menuUriRole.uri.eq(uri);
    }

    private BooleanExpression eqMethodType(String methodType) {
        return methodType == null ? null : QMenuUriRole.menuUriRole.methodType.eq(methodType);
    }
}
