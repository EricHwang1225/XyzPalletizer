package com.lgcns.smartwcs.req.cst.repository;

import com.lgcns.smartwcs.req.cst.model.CstReq;
import com.lgcns.smartwcs.req.cst.model.ids.CstReqId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CstReqRepository extends JpaRepository<CstReq, CstReqId> {
}
