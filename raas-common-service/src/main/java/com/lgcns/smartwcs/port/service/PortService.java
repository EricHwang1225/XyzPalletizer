/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.port.service;

import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.port.model.Port;
import com.lgcns.smartwcs.port.model.PortSearchCondition;
import com.lgcns.smartwcs.port.model.ids.PortPkId;
import com.lgcns.smartwcs.port.repository.PortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * <PRE>
 * Port 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class PortService {

    private static final String PORT_ID = "portId";
    private final PortRepository portRepository;
    private final Validator validator;

    /**
     * Port 목록을 조회한다.
     *
     * @param condition 검색조건
     * @param pageable  페이징 정보 객체
     * @return 페이징된 Port 목록.
     */
    public Page<Port> getList(PortSearchCondition condition, Pageable pageable) {
        return portRepository.findAllBySearch(condition, pageable);
    }

    /**
     * Port 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 Port 목록.
     */
    public List<Port> getUnpagedList(PortSearchCondition condition) {
        return portRepository.findAllBySearch(condition);
    }


    /**
     * 특정 키의 Port 정보 조회
     *
     * @param id Port의 복합 키
     * @return 조회된 Port 객체
     * @throws DataNotFoundException Port를 찾을 수 없을때 발생
     */
    public Port get(PortPkId id) throws DataNotFoundException {

        return portRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Port를 찾을 수 없습니다"));
    }

    /**
     * Port를 추가한다.
     *
     * @param port Port 정보
     * @return 추가된 Port 정보
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public Port create(Port port) throws DataDuplicateException, InvalidRequestException {
        this.validate(port);

        PortPkId id = PortPkId.builder().coCd(port.getCoCd()).cntrCd(port.getCntrCd()).eqpId(port.getEqpId()).portId(port.getPortId()).build();

        final Optional<Port> byId = portRepository.findById(id);
        if (byId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(PORT_ID, "Port ID : " + port.getPortId() + "가 존재합니다.");
            throw new DataDuplicateException(messageArray);
        }

        LocalDateTime now = LocalDateTime.now();

        port.setRegDt(now);
        port.setUpdDt(now);
        port.setUpdId(port.getRegId());

        return portRepository.save(port);
    }

    /**
     * Port를 수정한다.
     *
     * @param port 수정할 Port 정보
     * @return 수정된 Port 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public Port update(Port port) throws DataNotFoundException, InvalidRequestException {
        this.validate(port);

        PortPkId id = PortPkId.builder().coCd(port.getCoCd()).cntrCd(port.getCntrCd()).eqpId(port.getEqpId()).portId(port.getPortId()).build();

        final Optional<Port> byId = portRepository.findById(id);
        if (!byId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(PORT_ID, "Port ID : " + port.getPortId() + "를 찾을 수 없습니다.");
            throw new DataNotFoundException(messageArray);
        }

        Port prevPort = byId.get();

        // 변경가능한 항목만 변경
        prevPort.setPortNm(port.getPortNm());
        prevPort.setUseYn(port.getUseYn());
        prevPort.setPortDesc(port.getPortDesc());
        prevPort.setSortSeq(port.getSortSeq());
        prevPort.setPcIp(port.getPcIp());
        prevPort.setLightIp(port.getLightIp());
        prevPort.setLightUseYn(port.getLightUseYn());
        prevPort.setUserId(port.getUserId());

        prevPort.setWrInId(port.getWrInId());
        prevPort.setWrOutId(port.getWrOutId());

        LocalDateTime now = LocalDateTime.now();
        prevPort.setUpdDt(now);
        prevPort.setUpdId(port.getRegId());

        return portRepository.save(prevPort);
    }

    private void validate(Port port) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "coCd", port.getCoCd(), "column.coCd", 30, messageArray);
        validator.validateAndThrow(true, "cntrCd", port.getCntrCd(), "column.cntrCd", 30, messageArray);
        validator.validateAndThrow(true, "eqpId", port.getEqpId(), "column.eqpId", 30, messageArray);
        validator.validateAndThrow(true, PORT_ID, port.getPortId(), "column.portId", 30, messageArray);
        validator.validateAndThrow(true, "portNm", port.getPortNm(), "column.portNm", 50, messageArray);
        validator.validateAndThrow(false, "portDesc", port.getPortDesc(), "column.portDesc", 300, messageArray);
        validator.validateAndThrow(true, "useYn", port.getUseYn(), "column.useYn", 1, messageArray);
        validator.validateAndThrow(false, "pcIp", port.getPcIp(), "column.pcIp", 100, messageArray);
        validator.validateAndThrow(false, "lightIp", port.getLightIp(), "column.lightIp", 100, messageArray);
        validator.validateAndThrow(false, "wrInId", port.getUserId(), "column.wrInId", 30, messageArray);
        validator.validateAndThrow(false, "wrOutId", port.getUserId(), "column.wrOutId", 30, messageArray);
        validator.validateAndThrow(false, "userId", port.getUserId(), "column.userId", 30, messageArray);
        validator.validateAndThrow(true, "regId", port.getRegId(), "column.regId", 30, messageArray);

        validator.validateAndThrow(false, "sortSeq", port.getSortSeq(), "column.sortSeq", 9, messageArray);
    }
}
