package com.lgcns.smartwcs.req.sku.controller;

import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.utils.CommonConstants;
import com.lgcns.smartwcs.req.sku.model.SkuReq;
import com.lgcns.smartwcs.req.sku.model.SkuReqDTO;
import com.lgcns.smartwcs.req.sku.service.SkuReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/req/sku")
@RequiredArgsConstructor
public class SkuReqController {
    private final SkuReqService skuReqService;

    @GetMapping
    public CommonJsonResponse getUnPagedList(SkuReqDTO condition, CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData(skuReqService.getUnpagedList(condition));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(CommonConstants.MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonJsonResponse save(@RequestBody SkuReqDTO skuReqDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataDuplicateException {
        SkuReq skuReq = skuReqDTO.dtoMapping();

        commonJsonResponse.setData(skuReqService.create(skuReq));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(CommonConstants.MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @PostMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    public CommonJsonResponse update(@RequestBody SkuReqDTO skuReqDTO, CommonJsonResponse commonJsonResponse)
            throws InvalidRequestException, DataNotFoundException {
        SkuReq skuReq = skuReqDTO.dtoMapping();

        commonJsonResponse.setData(skuReqService.update(skuReq));
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(CommonConstants.MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @PostMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public CommonJsonResponse delete(@RequestBody SkuReqDTO skuReqDTO, CommonJsonResponse commonJsonResponse)
            throws DataNotFoundException {
        SkuReq skuReq = skuReqDTO.dtoMapping();

        skuReqService.delete(skuReq);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(CommonConstants.MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

    @PostMapping(value = "/requestSku")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonJsonResponse requestSku(@RequestBody SkuReqDTO skuReqDTO, CommonJsonResponse commonJsonResponse) {

        skuReqService.sendSkuRequest(skuReqDTO);
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(CommonConstants.MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }
}
