/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.port.repository.impl;

import com.lgcns.smartwcs.common.utils.QueryDslUtils;
import com.lgcns.smartwcs.port.model.Port;
import com.lgcns.smartwcs.port.model.PortSearchCondition;
import com.lgcns.smartwcs.port.model.QPort;
import com.lgcns.smartwcs.port.repository.PortRepositoryCustom;
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
public class PortRepositoryCustomImpl implements PortRepositoryCustom {

    /**
     * JPA 쿼리용 객체.
     */
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    /**
     * 검색 조건에 따로 사용자를 검색한다.
     *
     * @param condition 검색조건
     * @param pageable  페이징 조건
     * @return 페이징된 사용자 목록.
     */
    @Override
    public Page<Port> findAllBySearch(PortSearchCondition condition, Pageable pageable) {

        QueryResults<Port> result = jpaQueryFactory
                .selectFrom(QPort.port)
                .where(
                        eqCoCd(condition.getCoCd()),
                        eqCntrCd(condition.getCntrCd()),
                        eqEqpId(condition.getEqpId()),
                        eqPortId(condition.getPortId()),
                        ctPortNm(condition.getPortNm()),
                        eqUserId(condition.getUserId()),
                        eqUseYn(condition.getUseYn())
                )
                .orderBy(
                        QueryDslUtils.getAllOrderSpecifiers(QPort.port, pageable)
                                .stream().toArray(OrderSpecifier[]::new)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    /**
     * 검색 조건에 따로 사용자를 검색한다.
     *
     * @param condition 검색조건
     * @return 페이징된 사용자 목록.
     */
    @Override
    public List<Port> findAllBySearch(PortSearchCondition condition) {

        return jpaQueryFactory
                .selectFrom(QPort.port)
                .where(
                        eqCoCd(condition.getCoCd()),
                        eqCntrCd(condition.getCntrCd()),
                        eqEqpId(condition.getEqpId()),
                        eqPortId(condition.getPortId()),
                        ctPortNm(condition.getPortNm()),
                        eqUserId(condition.getUserId()),
                        eqUseYn(condition.getUseYn())
                ).fetch();

    }


    private BooleanExpression eqCoCd(String coCd) {
        return coCd == null ? null : QPort.port.coCd.eq(coCd);
    }

    private BooleanExpression eqCntrCd(String cntrCd) {
        return cntrCd == null ? null : QPort.port.cntrCd.eq(cntrCd);
    }

    private BooleanExpression eqEqpId(String eqpId) {
        return eqpId == null ? null : QPort.port.eqpId.eq(eqpId);
    }

    private BooleanExpression eqPortId(String portId) {
        return portId == null ? null : QPort.port.portId.eq(portId);
    }

    private BooleanExpression ctPortNm(String portNm) {
        return portNm == null ? null : QPort.port.portNm.contains(portNm);
    }

    private BooleanExpression eqUseYn(String useYn) {
        return useYn == null ? null : QPort.port.useYn.eq(useYn);
    }

    private BooleanExpression eqUserId(String userId) {
        return userId == null ? null : QPort.port.userId.eq(userId);
    }
}
