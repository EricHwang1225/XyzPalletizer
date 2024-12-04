package com.lgcns.smartwcs.req.sku.repository;

import com.lgcns.smartwcs.req.sku.model.SkuReqBcdMaster;
import com.lgcns.smartwcs.req.sku.model.SkuReqDTO;
import com.lgcns.smartwcs.req.sku.model.SkuReqMaster;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SkuReqMapper {
    List<SkuReqDTO> getSkuReqList(SkuReqDTO condition);

    List<SkuReqMaster> getSkuReqMaster(SkuReqDTO skuReqDTO);

    List<SkuReqBcdMaster> getSkuReqBcdMaster(SkuReqMaster skuReqMaster);

    void updateWcsSendSkuMaster(SkuReqDTO skuReqDTO);
}
