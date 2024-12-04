/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.user.repository.impl;

import com.lgcns.smartwcs.user.model.QUser;
import com.lgcns.smartwcs.user.model.User;
import com.lgcns.smartwcs.user.repository.UserRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <PRE>
 * 사용자 검색용 QueryDSL Custom 구현 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    /**
     * JPA 쿼리용 객체.
     */
    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Override
    public List<User> findAllbyUserId(String userId) {
        return jpaQueryFactory
                .selectFrom(QUser.user)
                .where(
                        eqUserId(userId)
                ).fetch();
    }

    private BooleanExpression eqUserId(String userId) {
        return userId == null ? null : QUser.user.userId.eq(userId);
    }
}
