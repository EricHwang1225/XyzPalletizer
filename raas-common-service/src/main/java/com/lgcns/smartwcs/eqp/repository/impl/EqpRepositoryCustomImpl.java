/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.eqp.repository.impl;


import com.lgcns.smartwcs.eqp.model.Eqp;
import com.lgcns.smartwcs.eqp.model.EqpSearchCondition;
import com.lgcns.smartwcs.eqp.model.QEqp;
import com.lgcns.smartwcs.eqp.repository.EqpRepositoryCustom;
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
public class EqpRepositoryCustomImpl implements EqpRepositoryCustom {

    /**
     * JPA 쿼리용 객체.
     */
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Eqp> findAllBySearch(EqpSearchCondition condition) {
        return jpaQueryFactory
                .selectFrom(QEqp.eqp)
                .where(
                        eqCoCd(condition.getCoCd()),
                        eqCntrCd(condition.getCntrCd()),
                        eqEqpId(condition.getEqpId()),
                        eqUseYn(condition.getUseYn()),
                        eqIfServIp(condition.getIfServIp()),
                        eqEqpTypeCd(condition.getEqpTypeCd())
                ).fetch();
    }

    private BooleanExpression eqCoCd(String coCd) {
        return coCd == null ? null : QEqp.eqp.coCd.eq(coCd);
    }

    private BooleanExpression eqCntrCd(String cntrCd) {
        return cntrCd == null ? null : QEqp.eqp.cntrCd.eq(cntrCd);
    }

    private BooleanExpression eqEqpId(String eqpId) {
        return eqpId == null ? null : QEqp.eqp.eqpId.eq(eqpId);
    }

    private BooleanExpression eqUseYn(String useYn) {
        return useYn == null ? null : QEqp.eqp.useYn.eq(useYn);
    }

    private BooleanExpression eqIfServIp(String ifServIp) {
        return ifServIp == null ? null : QEqp.eqp.ifServIp.eq(ifServIp);
    }

    private BooleanExpression eqEqpTypeCd(String eqpTypeCd) {
        return eqpTypeCd == null ? null : QEqp.eqp.eqpTypeCd.eq(eqpTypeCd);
    }
}
