/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.wms_url.service;


import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.wms_url.model.WmsUrl;
import com.lgcns.smartwcs.wms_url.model.WmsUrlSearchCondition;
import com.lgcns.smartwcs.wms_url.repository.WmsUrlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * WmsUrl 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class WmsUrlService {

    private final WmsUrlMapper wmsUrlMapper;
    private final Validator validator;

    /**
     * WmsUrl 목록을 조회한다.
     *
     * @param condition 검색조건
     * @return 페이징된 WmsUrl 목록.
     */
    public List<Map<String, Object>> getUnpagedList(WmsUrlSearchCondition condition) {
        return wmsUrlMapper.getWmsUrlList(condition);
    }

    /**
     * WmsUrl를 수정한다.
     *
     * @param wmsUrl 수정할 WmsUrl 정보
     * @return 수정된 WmsUrl 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void merge(WmsUrl wmsUrl) throws InvalidRequestException {
        this.validate(wmsUrl);

        LocalDateTime now = LocalDateTime.now();
        wmsUrl.setRegDt(now);

        wmsUrlMapper.saveWmsUrl(wmsUrl);
    }

    private void validate(WmsUrl wmsUrl) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "coCd", wmsUrl.getCoCd(), "column.coCd", 30, messageArray);
        validator.validateAndThrow(true, "wmsIfId", wmsUrl.getWmsIfId(), "column.wmsIfId", 30, messageArray);
        validator.validateAndThrow(false, "wmsIfUrl", wmsUrl.getWmsIfUrl(), "column.wmsIfUrl", 1000, messageArray);
        validator.validateAndThrow(true, "regId", wmsUrl.getRegId(), "column.regId", 30, messageArray);
    }
}
