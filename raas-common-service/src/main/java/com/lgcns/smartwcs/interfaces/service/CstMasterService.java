/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.interfaces.service;

import com.lgcns.smartwcs.interfaces.model.CstMaster;
import com.lgcns.smartwcs.interfaces.repository.CstReceiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * <PRE>
 * 화주 정보를 저장하기 위한 Service 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
public class CstMasterService {

    /**
     * 화주 Mst Mapper
     */
    @Autowired
    private CstReceiveMapper cstReceiveMapper;

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void createCstInfo(CstMaster cst) {
        cstReceiveMapper.save(cst);
    }

}
