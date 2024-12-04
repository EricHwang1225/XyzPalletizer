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
import com.lgcns.smartwcs.eqp.model.Eqp;
import com.lgcns.smartwcs.eqp.model.ids.EquipmentId;
import com.lgcns.smartwcs.eqp.repository.EqpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    /**
     * 특정 키의 Eqp 정보 조회
     *
     * @param id Eqp의 복합 키
     * @return 조회된 Eqp 객체
     * @throws DataNotFoundException Eqp를 찾을 수 없을때 발생
     */
    public Eqp get(EquipmentId id) throws DataNotFoundException {

        return eqpRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Eqp를 찾을 수 없습니다"));
    }

}
