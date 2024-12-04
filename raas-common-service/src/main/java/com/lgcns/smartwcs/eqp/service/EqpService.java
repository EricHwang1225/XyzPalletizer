/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.eqp.service;

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.eqp.model.Eqp;
import com.lgcns.smartwcs.eqp.model.EqpSearchCondition;
import com.lgcns.smartwcs.eqp.model.ids.EqpuipmentId;
import com.lgcns.smartwcs.eqp.repository.EqpRepository;
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
 * Eqp 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class EqpService {

    /**
     * Eqp Repository 객체
     */

    private final EqpRepository eqpRepository;
    private final Validator validator;
    private final MessageSourceAccessor accessor;

    private static final String LANG_NOT_FOUND = "validation.notFound";


    /**
     * Eqp 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 Eqp 목록.
     */
    public List<Eqp> getUnpagedList(EqpSearchCondition condition) {

        return eqpRepository.findAllBySearch(condition);
    }

    /**
     * Eqp를 수정한다.
     *
     * @param eqp 수정할 Eqp 정보
     * @return 수정된 Eqp 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public Eqp updateTote(Eqp eqp) throws DataNotFoundException, InvalidRequestException {
        this.validateTote(eqp);

        EqpuipmentId id = EqpuipmentId.builder().coCd(eqp.getCoCd()).cntrCd(eqp.getCntrCd()).eqpId(eqp.getEqpId()).build();

        final Optional<Eqp> byId = eqpRepository.findById(id);
        if (!byId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put("eqpId", "Eqp ID : " + eqp.getEqpId() + accessor.getMessage(LANG_NOT_FOUND));
            throw new DataNotFoundException(messageArray);
        }

        Eqp prevEqp = byId.get();

        // 변경가능한 항목만 변경
        prevEqp.setToteRegex(eqp.getToteRegex());
        prevEqp.setToteUnavblMin(eqp.getToteUnavblMin());

        LocalDateTime now = LocalDateTime.now();
        prevEqp.setUpdDt(now);
        prevEqp.setUpdId(eqp.getRegId());

        return eqpRepository.save(prevEqp);
    }

    /**
     * 특정 키의 Eqp 정보 조회
     *
     * @param id Eqp의 복합 키
     * @return 조회된 Eqp 객체
     * @throws DataNotFoundException Eqp를 찾을 수 없을때 발생
     */
    public Eqp get(EqpuipmentId id) throws DataNotFoundException {

        return eqpRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Eqp를 찾을 수 없습니다"));
    }

    private void validateTote(Eqp eqp) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "coCd", eqp.getCoCd(), "column.coCd", 30, messageArray);
        validator.validateAndThrow(true, "cntrCd", eqp.getCntrCd(), "column.cntrCd", 30, messageArray);
        validator.validateAndThrow(true, "eqpId", eqp.getEqpId(), "column.eqpId", 30, messageArray);
        validator.validateAndThrow(false, "toteRegex", eqp.getToteRegex(), "column.toteRegex", 100, messageArray);
        validator.validateAndThrow(false, "toteUnavblMin", eqp.getToteUnavblMin(), "column.toteUnavblMin", 9, messageArray);
        validator.validateAndThrow(true, "regId", eqp.getRegId(), "column.regId", 30, messageArray);
    }

}
