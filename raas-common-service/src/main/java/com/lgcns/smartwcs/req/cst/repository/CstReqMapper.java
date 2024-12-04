package com.lgcns.smartwcs.req.cst.repository;

import com.lgcns.smartwcs.req.cst.model.CstReqDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CstReqMapper {
    List<CstReqDTO> getCstReqList(CstReqDTO condition);

    void updateWcsCstRequest(CstReqDTO inbdReqDTO);
}
