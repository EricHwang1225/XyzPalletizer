/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.utils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * QueryDSL에서 Order 정보를 조합하기 위한 유틸리티 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@UtilityClass
public class QueryDslUtils {

    /**
     * 정렬 컬럼을 생성하기 위한 메소드.
     * @param order
     * @param parent
     * @param fieldName
     * @return
     */
    public static OrderSpecifier<EntityObject> getSortedColumn(Order order, Path<?> parent, String fieldName) {
        Path<EntityObject> fieldPath = Expressions.path(EntityObject.class, parent, fieldName);
        return new OrderSpecifier<>(order, fieldPath);
    }

    /**
     * 모든 정렬 정보를 조합하기 위한 메소드.
     * @param target
     * @param pageable
     * @return
     */
    public static List<OrderSpecifier<EntityObject>> getAllOrderSpecifiers(Path<?> target, Pageable pageable) {

        List<OrderSpecifier<EntityObject>> orders = new ArrayList<>();

        if(!ObjectUtils.isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                OrderSpecifier<EntityObject> os = getSortedColumn(direction, target, order.getProperty());
                orders.add(os);
            }
        }

        return orders;
    }
}

class EntityObject extends Object implements Comparable<Object> {

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}