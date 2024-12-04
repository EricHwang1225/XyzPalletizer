/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.cst.repository.impl;

import com.lgcns.smartwcs.cst.model.QCst;
import com.lgcns.smartwcs.common.utils.QueryDslUtils;
import com.lgcns.smartwcs.cst.model.Cst;
import com.lgcns.smartwcs.cst.model.CstSearchCondition;
import com.lgcns.smartwcs.cst.repository.CstRepositoryCustom;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <PRE>
 * 사용자 검색용 QueryDSL Custom 구현 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
public class CstRepositoryCustomImpl implements CstRepositoryCustom {

    /**
     * JPA 쿼리용 객체.
     */
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    /**
     * 검색 조건에 따로 사용자를 검색한다.
     *
     * @param condition 검색조건
     * @param pageable 페이징 조건
     * @return 페이징된 사용자 목록.
     */
    @Override
    public Page<Cst> findAllBySearch(CstSearchCondition condition, Pageable pageable) {

        QueryResults<Cst> result = jpaQueryFactory
                .selectFrom(QCst.cst)
                .where(
                        eqCoCd(condition.getCoCd()),
                        ctCstCd(condition.getCstCd()),
                        ctCstNm(condition.getCstNm()),
                        eqUseYn(condition.getUseYn())
                )
                .orderBy(
                        QueryDslUtils.getAllOrderSpecifiers(QCst.cst, pageable)
                                .stream().toArray(OrderSpecifier[]::new)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public List<Cst> findAllBySearch(CstSearchCondition condition) {
        return jpaQueryFactory
                .selectFrom(QCst.cst)
                .where(
                        eqCoCd(condition.getCoCd()),
                        ctCstCd(condition.getCstCd()),
                        ctCstNm(condition.getCstNm()),
                        eqUseYn(condition.getUseYn())
                ).fetch();
    }

    private BooleanExpression eqCoCd(String coCd) {
        return coCd == null ? null : QCst.cst.coCd.eq(coCd);
    }

    private BooleanExpression ctCstCd(String cstCd) {
        return cstCd == null ? null : QCst.cst.cstCd.contains(cstCd);
    }

    private BooleanExpression ctCstNm(String cstNm) {
        return cstNm == null ? null : QCst.cst.cstNm.contains(cstNm);
    }

    private BooleanExpression eqUseYn(String useYn) {
        return useYn == null ? null : QCst.cst.useYn.eq(useYn);
    }
}
