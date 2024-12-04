/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.center.repository.impl;

import com.lgcns.smartwcs.center.model.Center;
import com.lgcns.smartwcs.center.model.QCenter;
import com.lgcns.smartwcs.center.repository.CenterRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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
public class CenterRepositoryCustomImpl implements CenterRepositoryCustom {

    /**
     * JPA 쿼리용 객체.
     */
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Center> findAllbyCenterNm(String coCd, String cntrNm) {
        return jpaQueryFactory
                .selectFrom(QCenter.center)
                .where(
                        eqCoCd(coCd),
                        eqCntrNm(cntrNm)
                ).fetch();
    }

    private BooleanExpression eqCoCd(String coCd) {
        return coCd == null ? null : QCenter.center.coCd.eq(coCd);
    }

    private BooleanExpression eqCntrNm(String cntrNm) {
        return cntrNm == null ? null : QCenter.center.cntrNm.eq(cntrNm);
    }


}
