package com.lgcns.smartwcs.req.cst.controller;

import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.CommonConstants;
import com.lgcns.smartwcs.req.cst.model.CstReq;
import com.lgcns.smartwcs.req.cst.model.CstReqDTO;
import com.lgcns.smartwcs.req.cst.service.CstReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/req/cst")
@RequiredArgsConstructor
public class CstReqController {
    private final CstReqService cstReqService;

    @GetMapping
    public CommonJsonResponse getUnPagedList(CstReqDTO condition, CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData(cstReqService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(CommonConstants.MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonJsonResponse save(@RequestBody CstReqDTO cstReqDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataDuplicateException {
        CstReq cstReq = cstReqDTO.dtoMapping();

        commonJsonResponse.setData(cstReqService.create(cstReq));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(CommonConstants.MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @PostMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    public CommonJsonResponse update(@RequestBody CstReqDTO cstReqDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataNotFoundException {
        CstReq cstReq = cstReqDTO.dtoMapping();

        commonJsonResponse.setData(cstReqService.update(cstReq));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(CommonConstants.MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @PostMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public CommonJsonResponse delete(@RequestBody CstReqDTO cstReqDTO, CommonJsonResponse commonJsonResponse)
            throws DataNotFoundException {
        CstReq cstReq = cstReqDTO.dtoMapping();

        cstReqService.delete(cstReq);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(CommonConstants.MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @PostMapping(value = "/sendOwnerMasterInfo")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonJsonResponse sendOwnerMasterInfo(@RequestBody CstReqDTO cstReqDTO, CommonJsonResponse commonJsonResponse) {

        cstReqService.sendOwnerMasterInfo(cstReqDTO);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(CommonConstants.MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }
}
