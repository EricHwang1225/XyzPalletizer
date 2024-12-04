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

import com.lgcns.smartwcs.sku.model.SkuBcd;
import com.lgcns.smartwcs.sku.model.SkuBcdSearchCondition;
import com.lgcns.smartwcs.sku.repository.SkuBcdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <PRE>
 * Sku Barcode  서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
public class SkuBcdService {

    /**
     * Sku Barcode  Repository 객체
     */
    @Autowired
    private SkuBcdRepository skuBcdRepository;


    /**
     * 공통코드 목록을 얻어온다.
     *
     * @param condition 검색 조건 객체
     * @return 공통 코드 목록
     */
    public List<SkuBcd> getUnPagedList(SkuBcdSearchCondition condition) {

        return skuBcdRepository.findAllBySearch(condition);
    }

}
