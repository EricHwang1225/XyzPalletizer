/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * <PRE>
 * QueryDSL 설정을 위한 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Configuration
public class QueryDSLConfig {

    /**
     * Persistence 객체
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * JPA Query 실행 객체.
     * @return
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
