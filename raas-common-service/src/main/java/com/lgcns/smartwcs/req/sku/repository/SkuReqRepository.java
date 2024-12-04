package com.lgcns.smartwcs.req.sku.repository;

import com.lgcns.smartwcs.req.sku.model.SkuReq;
import com.lgcns.smartwcs.req.sku.model.ids.SkuReqId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuReqRepository extends JpaRepository<SkuReq, SkuReqId> {
}
