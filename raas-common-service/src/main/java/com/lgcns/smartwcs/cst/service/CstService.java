/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.cst.service;

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.cst.model.Cst;
import com.lgcns.smartwcs.cst.model.CstSearchCondition;
import com.lgcns.smartwcs.cst.model.ids.CstId;
import com.lgcns.smartwcs.cst.repository.CstRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <PRE>
 * Cst 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
public class CstService {

    /**
     * Cst Repository 객체
     */
    @Autowired
    private CstRepository cstRepository;

    /**
     * Cst 목록을 조회한다.
     *
     * @param condition 검색조건
     * @param pageable  페이징 정보 객체
     * @return 페이징된 Cst 목록.
     */
    public Page<Cst> getList(CstSearchCondition condition, Pageable pageable) {
        return cstRepository.findAllBySearch(condition, pageable);
    }

    /**
     * Cst 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 Cst 목록.
     */
    public List<Cst> getUnpagedList(CstSearchCondition condition) {

        List<Cst> customerList = cstRepository.findAllBySearch(condition);

        return customerList;
    }

    /**
     * 특정 키의 Cst 정보 조회
     *
     * @param id Cst의 복합 키
     * @return 조회된 Cst 객체
     * @throws DataNotFoundException Cst를 찾을 수 없을때 발생
     */
    public Cst get(CstId id) throws DataNotFoundException {

        return cstRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("화주를 찾을 수 없습니다"));
    }


}
