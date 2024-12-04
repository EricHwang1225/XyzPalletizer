/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.role.repository.impl;


import com.lgcns.smartwcs.common.utils.QueryDslUtils;
import com.lgcns.smartwcs.role.model.QRole;
import com.lgcns.smartwcs.role.model.Role;
import com.lgcns.smartwcs.role.model.RoleSearchCondition;
import com.lgcns.smartwcs.role.repository.RoleRepositoryCustom;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <PRE>
 * 공통 코드 검색용 QueryDSL Custom 구현 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@RequiredArgsConstructor
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {

    /**
     * JPA 쿼리용 객체.
     */
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    /**
     * 검색 조건에 해당하는 목록을 반환한다.
     * 검색 조건을 만족시키는 다이나믹 QueryDSL 예제
     * @param condition 검색 조건 객체
     * @param pageable 페이징 정보 객체
     * @return 페이징된 Role 코드.
     */

    @Override
    public Page<Role> findAllBySearch(RoleSearchCondition condition, Pageable pageable) {
        QueryResults<Role> result = jpaQueryFactory
                .selectFrom(QRole.role)
                .where(
                        eqCoCd(condition.getCoCd()),
                        eqCntrCd(condition.getCntrCd()),
                        ctRoleCd(condition.getRoleCd()),
                        ctRoleNm(condition.getRoleNm()),
                        eqUseYn(condition.getUseYn())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        QueryDslUtils.getAllOrderSpecifiers(QRole.role, pageable)
                                .stream().toArray(OrderSpecifier[]::new)
                )
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public List<Role> findAllBySearch(RoleSearchCondition condition) {
        return jpaQueryFactory
                .selectFrom(QRole.role)
                .where(
                        eqCoCd(condition.getCoCd()),
                        eqCntrCd(condition.getCntrCd()),
                        ctRoleCd(condition.getRoleCd()),
                        ctRoleNm(condition.getRoleNm()),
                        eqUseYn(condition.getUseYn())
                ).fetch();
    }

    private BooleanExpression eqCoCd(String coCd) {
        return coCd == null ? null : QRole.role.coCd.eq(coCd);
    }

    private BooleanExpression eqCntrCd(String cntrCd) {
        return cntrCd == null ? null : QRole.role.cntrCd.eq(cntrCd);
    }

    private BooleanExpression ctRoleCd (String roleCd) {
        return roleCd == null ? null : QRole.role.roleCd.contains(roleCd);
    }

    private BooleanExpression ctRoleNm (String roleNm) {
        return roleNm == null ? null : QRole.role.roleNm.contains(roleNm);
    }

    private BooleanExpression eqUseYn(String useYn) {
        return useYn == null ? null : QRole.role.useYn.eq(useYn);
    }
    

}
