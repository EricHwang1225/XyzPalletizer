/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.multi_language.code.service;

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.multi_language.code.model.MultiLangCode;
import com.lgcns.smartwcs.multi_language.code.repository.MultiLangCodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * <PRE>
 * MultiLang Code 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class MultiLangCodeService {

    private final MultiLangCodeMapper multiLangCodeMapper;
    private final Validator validator;

    /**
     * MultiLang Code 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 MultiLang Code 목록.
     */
    public List<MultiLangCode> getUnpagedList(MultiLangCode condition) {

        return multiLangCodeMapper.getMultiLangCodeList(condition);
    }

    /**
     * Excel Upload를 위해서 대분류 코드와 상세 코드를 합쳐서 MultiLang Code 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 MultiLang Code 목록.
     */
    public List<MultiLangCode> mergedList(MultiLangCode condition) {

        return multiLangCodeMapper.getMultiLangCodeExcelList(condition);
    }


    /**
     * MultiLang에 Code를 Mapping한다.
     *
     * @param multiLangCode 수정할 MultiLang Code 정보
     * @return 수정된 MultiLang Code 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void merge(MultiLangCode multiLangCode) throws InvalidRequestException {
        this.validate(multiLangCode);

        LocalDateTime now = LocalDateTime.now();
        multiLangCode.setRegDt(now);

        multiLangCodeMapper.saveMultiLangCode(multiLangCode);
    }

    private void validate(MultiLangCode multiLangCode) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "comDtlCd", multiLangCode.getComDtlCd(), "column.comDtlCd", 30, messageArray);
        validator.validateAndThrow(true, "regId", multiLangCode.getRegId(), "column.regId", 30, messageArray);
    }
}
