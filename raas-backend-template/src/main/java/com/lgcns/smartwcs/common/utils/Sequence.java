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

import com.lgcns.smartwcs.common.repository.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * MySQL에서 Sequence Transaction을 분리해서 가져오기 위한 유틸리티 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
public class Sequence {

    @Autowired
    SequenceMapper sequenceMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public Integer getInbdUid() {
        return sequenceMapper.getInbdUid();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public Integer getObndUid() {
        return sequenceMapper.getObndUid();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public Integer getSttkUid() {
        return sequenceMapper.getSttkUid();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public Integer getTaskUid() {
        return sequenceMapper.getTaskUid();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public Integer getTaskGroupId() {
        return sequenceMapper.getTaskGroupId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public Integer getTaskId() {
        return sequenceMapper.getTaskId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public Integer getPtwyUid() {
        return sequenceMapper.getPtwyUid();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public Integer getPickUid() {
        return sequenceMapper.getPickUid();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public Integer getWcsTrackingId() {
        return sequenceMapper.getWcsTrackingId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = RuntimeException.class)
    public Integer getAbnormUid() {
        return sequenceMapper.getAbnormUid();
    }
}
