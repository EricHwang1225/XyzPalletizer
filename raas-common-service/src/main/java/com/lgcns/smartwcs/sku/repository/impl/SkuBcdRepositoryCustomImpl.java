/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.sku.repository.impl;

import com.lgcns.smartwcs.sku.model.QSkuBcd;
import com.lgcns.smartwcs.sku.model.SkuBcd;
import com.lgcns.smartwcs.sku.model.SkuBcdSearchCondition;
import com.lgcns.smartwcs.sku.repository.SkuBcdRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <PRE>
 * Sku Barcode 검색용 QueryDSL Custom 구현 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@RequiredArgsConstructor
public class SkuBcdRepositoryCustomImpl implements SkuBcdRepositoryCustom {

    /**
     * JPA 쿼리용 객체.
     */
    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Override
    public List<SkuBcd> findAllBySearch(SkuBcdSearchCondition condition) {
        return jpaQueryFactory
                .selectFrom(QSkuBcd.skuBcd)
                .where(
                        eqCoCd(condition.getCoCd()),
                        eqCstCd(condition.getCstCd()),
                        eqSkuCd(condition.getSkuCd()),
                        ctBcd(condition.getBcd()),
                        eqUseYn(condition.getUseYnDt())
                ).fetch();
    }

    private BooleanExpression eqCoCd(String coCd) {
        return coCd == null ? null : QSkuBcd.skuBcd.coCd.eq(coCd);
    }

    private BooleanExpression eqCstCd(String cstCd) {
        return cstCd == null ? null : QSkuBcd.skuBcd.cstCd.eq(cstCd);
    }

    private BooleanExpression eqSkuCd(String skuCd) {
        return skuCd == null ? null : QSkuBcd.skuBcd.skuCd.eq(skuCd);
    }

    private BooleanExpression ctBcd(String bcd) {
        return bcd == null ? null : QSkuBcd.skuBcd.bcd.contains(bcd);
    }

    private BooleanExpression eqUseYn(String bcdUseYn) {
        return bcdUseYn == null ? null : QSkuBcd.skuBcd.useYn.eq(bcdUseYn);
    }
}
