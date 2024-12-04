/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.sku.service;


import com.lgcns.smartwcs.sku.model.Sku;
import com.lgcns.smartwcs.sku.model.SkuSearchCondition;
import com.lgcns.smartwcs.sku.repository.SkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <PRE>
 * Sku 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
public class SkuService {

    /**
     * Sku Repository 객체
     */
    @Autowired
    private SkuMapper skuMapper;


    /**
     * Sku 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 Sku 목록.
     */
    public List<Sku> getUnpagedList(SkuSearchCondition condition) {

        return skuMapper.getSkuList(condition);
    }

}
