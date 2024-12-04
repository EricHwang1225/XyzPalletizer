/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.code.service;

import com.lgcns.smartwcs.code.model.Code;
import com.lgcns.smartwcs.code.model.CodeSearchCondition;
import com.lgcns.smartwcs.code.model.ids.CodeId;
import com.lgcns.smartwcs.code.repository.CodeMapper;
import com.lgcns.smartwcs.code.repository.CodeRepository;
import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import com.lgcns.smartwcs.common.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * <PRE>
 * Code 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class CodeService {

    private static final String COM_HDR_CD = "comHdrCd";
    private static final String COM_DTL_CD = "comDtlCd";

    private static final String LANG_COM_HDR_CD = "column.comHdrCd";
    private static final String LANG_COM_DTL_CD = "column.comDtlCd";
    private static final String LANG_NOT_FOUND = "validation.notFound";

    private final CodeRepository codeRepository;
    private final CodeMapper codeMapper;
    private final Validator validator;
    private final MessageSourceAccessor accessor;

    /**
     * Code 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 Code 목록.
     */
    public List<Code> getUnPagedList(CodeSearchCondition condition) {
        List<Code> codes = null;
        if (condition.getHdrFlag().equals("Y")) {
            condition.setComHdrCd(CommonUtils.replacePercentAndUnderbar(condition.getComHdrCd()));
            condition.setComHdrNm(CommonUtils.replacePercentAndUnderbar(condition.getComHdrNm()));
            condition.setComDtlCd(CommonUtils.replacePercentAndUnderbar(condition.getComDtlCd()));
            condition.setComDtlNm(CommonUtils.replacePercentAndUnderbar(condition.getComDtlNm()));

            codes = codeMapper.getCodeHdrList(condition);
        } else {
            condition.setCoCd("LGWCS");
            if (condition.getUseYnDt() == null || condition.getUseYnDt().equals(""))
                condition.setUseYnDt(null);

            if (condition.getLangCd() == null || condition.getLangCd().equals("") || condition.getLangCd().equals("ko"))
                codes = codeRepository.findAllBySearch(condition);
            else
                codes = codeMapper.getCodeLangList(condition);
        }

        return codes;
    }

    /**
     * Code 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 Code 목록.
     */
    public List<Code> getTranslatedCodeList(CodeSearchCondition condition) {
        return codeMapper.getCodeLangList(condition);
    }


    /**
     * Code를 추가한다.
     *
     * @param code Code 정보
     * @return 추가된 Code 정보
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public Code create(Code code) throws DataDuplicateException, InvalidRequestException {
        this.validate(code);

        CodeId id = CodeId.builder().coCd(code.getCoCd())
                .comHdrCd(code.getComHdrCd()).comDtlCd(code.getComDtlCd()).build();

        if (codeRepository.existsById(id)) {
            if (code.getHdrFlag().equals("Y")) {
                HashMap<String, String> messageArray = new HashMap<>();
                messageArray.put(COM_HDR_CD, accessor.getMessage(LANG_COM_HDR_CD) + " " + accessor.getMessage("validation.exist"));
                throw new DataDuplicateException(messageArray);
            } else {
                HashMap<String, String> messageArray = new HashMap<>();
                messageArray.put(COM_DTL_CD, accessor.getMessage(LANG_COM_DTL_CD) + " " + accessor.getMessage("validation.exist"));
                throw new DataDuplicateException(messageArray);
            }
        }

        LocalDateTime now = LocalDateTime.now();

        code.setRegDt(now);
        code.setUpdDt(now);
        code.setUpdId(code.getRegId());

        return codeRepository.save(code);
    }

    /**
     * Code를 수정한다.
     *
     * @param code 수정할 Code 정보
     * @return 수정된 Code 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public Code update(Code code) throws DataNotFoundException, InvalidRequestException {
        this.validate(code);

        CodeId id = CodeId.builder().coCd(code.getCoCd())
                .comHdrCd(code.getComHdrCd()).comDtlCd(code.getComDtlCd()).build();

        final Optional<Code> byId = codeRepository.findById(id);
        if (!byId.isPresent()) {
            if (code.getHdrFlag().equals("Y")) {
                HashMap<String, String> messageArray = new HashMap<>();
                messageArray.put(COM_HDR_CD, accessor.getMessage(LANG_COM_HDR_CD) + " " + accessor.getMessage(LANG_NOT_FOUND));
                throw new DataNotFoundException(messageArray);
            } else {
                HashMap<String, String> messageArray = new HashMap<>();
                messageArray.put(COM_DTL_CD, accessor.getMessage(LANG_COM_DTL_CD) + " " + accessor.getMessage(LANG_NOT_FOUND));
                throw new DataNotFoundException(messageArray);
            }
        }

        Code prevCode = byId.get();
        prevCode.setComHdrNm(code.getComHdrNm());
        prevCode.setComDtlNm(code.getComDtlNm());
        prevCode.setUseYn(code.getUseYn());
        prevCode.setSortSeq(code.getSortSeq());

        prevCode.setUpdId(code.getRegId());

        return codeRepository.save(prevCode);
    }

    /**
     * Code를 삭제한다.
     *
     * @param code 삭제할 Code 정보
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void delete(Code code) throws DataNotFoundException {

        CodeId id = CodeId.builder().coCd(code.getCoCd())
                .comHdrCd(code.getComHdrCd()).comDtlCd(code.getComDtlCd()).build();

        if (!codeRepository.existsById(id)) {
            if (code.getHdrFlag().equals("Y")) {
                HashMap<String, String> messageArray = new HashMap<>();
                messageArray.put(COM_HDR_CD, accessor.getMessage(LANG_COM_HDR_CD) + " " + accessor.getMessage(LANG_NOT_FOUND));
                throw new DataNotFoundException(messageArray);
            } else {
                HashMap<String, String> messageArray = new HashMap<>();
                messageArray.put(COM_DTL_CD, accessor.getMessage(LANG_COM_DTL_CD) + " " + accessor.getMessage(LANG_NOT_FOUND));
                throw new DataNotFoundException(messageArray);
            }
        }

        //Header를 지우는 경우에는 Detail도 찾아서 다 지워주고 detail의 경우에는 단건만 지운다
        List<Code> codeList;
        if (code.getHdrFlag().equals("Y")) {
            codeList = codeRepository.findAllbyComHdrCd(code);
            codeRepository.deleteAll(codeList);
        } else {
            codeRepository.delete(code);
        }
    }

    @Transactional
    public List<Code> getListByCondition(String comHdrCd, String langCd) {
        CodeSearchCondition codeSearchCondition = CodeSearchCondition.builder()
                .hdrFlag("N")
                .comHdrCd(comHdrCd)
                .langCd(langCd)
                .build();

        return this.getUnPagedList(codeSearchCondition);
    }

    private void validate(Code code) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "coCd", code.getCoCd(), "column.coCd", 30, messageArray);
        validator.validateAndThrow(true, COM_HDR_CD, code.getComHdrCd(), LANG_COM_HDR_CD, 30, messageArray);
        validator.validateAndThrow(true, "comHdrNm", code.getComHdrNm(), "column.comHdrNm", 100, messageArray);
        validator.validateAndThrow(true, COM_DTL_CD, code.getComDtlCd(), LANG_COM_DTL_CD, 30, messageArray);
        validator.validateAndThrow(true, "comDtlNm", code.getComDtlNm(), "column.comDtlNm", 100, messageArray);
        validator.validateAndThrow(true, "useYn", code.getUseYn(), "column.useYn", 1, messageArray);

        validator.validateAndThrow(false, "sortSeq", code.getSortSeq(), "column.sortSeq", 9, messageArray);
    }
}
