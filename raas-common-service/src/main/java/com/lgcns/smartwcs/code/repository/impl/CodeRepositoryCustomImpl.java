/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.code.repository.impl;

import com.lgcns.smartwcs.code.model.Code;
import com.lgcns.smartwcs.code.model.CodeSearchCondition;
import com.lgcns.smartwcs.code.model.QCode;
import com.lgcns.smartwcs.code.repository.CodeRepositoryCustom;
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
public class CodeRepositoryCustomImpl implements CodeRepositoryCustom {

    /**
     * JPA 쿼리용 객체.
     */
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Code> findAllBySearch(CodeSearchCondition condition) {
        return jpaQueryFactory
                .selectFrom(QCode.code)
                .where(
                        eqCoCd(condition.getCoCd()),
                        eqComHdrCd(condition.getComHdrCd()),
                        ctComDtlCd(condition.getComDtlCd()),
                        ctComHdrNm(condition.getComHdrNm()),
                        ctComDtlNm(condition.getComDtlNm()),
                        eqUseYn(condition.getUseYnDt()),
                        eqHdrFlag(condition.getHdrFlag())
                )
                .orderBy(QCode.code.sortSeq.asc())
                .fetch();
    }

    @Override
    public List<Code> findAllbyComHdrCd(Code code) {
        return jpaQueryFactory
                .selectFrom(QCode.code)
                .where(
                        eqCoCd(code.getCoCd()),
                        eqComHdrCd(code.getComHdrCd())
                ).fetch();
    }

    private BooleanExpression eqCoCd(String coCd) {
        return coCd == null ? null : QCode.code.coCd.eq(coCd);
    }

    private BooleanExpression eqComHdrCd(String comHdrCd) {
        return comHdrCd == null ? null : QCode.code.comHdrCd.eq(comHdrCd);
    }

    private BooleanExpression ctComDtlCd(String comDtlCd) {
        return comDtlCd == null ? null : QCode.code.comDtlCd.contains(comDtlCd);
    }

    private BooleanExpression ctComHdrNm(String comHdrNm) {
        return comHdrNm == null ? null : QCode.code.comHdrNm.contains(comHdrNm);
    }

    private BooleanExpression ctComDtlNm(String comDtlNm) {
        return comDtlNm == null ? null : QCode.code.comDtlNm.contains(comDtlNm);
    }

    private BooleanExpression eqUseYn(String useYn) {
        return useYn == null ? null : QCode.code.useYn.eq(useYn);
    }

    private BooleanExpression eqHdrFlag(String hdrFlag) {
        return hdrFlag == null ? null : QCode.code.hdrFlag.eq(hdrFlag);
    }


}
