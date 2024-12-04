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

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.cst.model.ids.CstId;
import com.lgcns.smartwcs.cst.service.CstService;
import com.lgcns.smartwcs.interfaces.model.SkuBcdMaster;
import com.lgcns.smartwcs.interfaces.model.SkuMaster;
import com.lgcns.smartwcs.interfaces.repository.SkuReceiveMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * <PRE>
 * Sku Master 및 Sku Barcode를 저장하기 위한 Service 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
public class SkuMasterService {

    /**
     * Sku Receive Mapper
     */

    private final SkuReceiveMapper skuReceiveMapper;


    private final CstService cstService;


    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void createSkuInfo(SkuMaster skuMaster) throws DataNotFoundException {

        CstId cstId = CstId.builder()
                .coCd(skuMaster.getCoCd())
                .cstCd(skuMaster.getCstCd())
                .build();

        cstService.get(cstId);

        skuReceiveMapper.save(skuMaster);

        List<SkuBcdMaster> list = skuMaster.getDetail();
        for (SkuBcdMaster detail : list) {
            detail.setCoCd(skuMaster.getCoCd());
            detail.setCstCd(skuMaster.getCstCd());
            detail.setSkuCd(skuMaster.getSkuCd());
            detail.setRegDt(skuMaster.getRegDt());
            detail.setRegId(skuMaster.getRegId());
            detail.setUpdDt(skuMaster.getRegDt());
            detail.setUpdId(skuMaster.getRegId());

            skuReceiveMapper.saveSkuBcd(detail);
        }
    }

}
