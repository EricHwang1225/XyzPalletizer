package com.lgcns.smartwcs.req.sku.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.smartwcs.common.client.WCSClient;
import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.model.InterfaceJsonRequest;
import com.lgcns.smartwcs.common.model.WcsResult;
import com.lgcns.smartwcs.common.utils.CommonConstants;
import com.lgcns.smartwcs.common.utils.Sequence;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.req.sku.model.SkuReq;
import com.lgcns.smartwcs.req.sku.model.SkuReqDTO;
import com.lgcns.smartwcs.req.sku.model.SkuReqMaster;
import com.lgcns.smartwcs.req.sku.model.ids.SkuReqId;
import com.lgcns.smartwcs.req.sku.repository.SkuReqMapper;
import com.lgcns.smartwcs.req.sku.repository.SkuReqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkuReqService {

    private final Sequence sequence;

    private DecimalFormat trackingUidFormat = new DecimalFormat("WMS00000000000");

    private ObjectMapper objectMapper = new ObjectMapper();

    private final WCSClient wcsClient;

    private static final String BCD_NO = "bcdNo";
    private static final String LANG_CST_CD = "column.cstCd";
    private static final String LANG_SKU_CD = "column.skuCd";
    private static final String LANG_BCD_NO = "column.bcdNo";
    private static final String LANG_NOT_FOUND = "validation.notFound";

    private final SkuReqMapper skuReqMapper;
    private final SkuReqRepository skuReqRepository;
    private final MessageSourceAccessor accessor;
    private final Validator validator;

    public List<SkuReqDTO> getUnpagedList(SkuReqDTO condition) {
        return skuReqMapper.getSkuReqList(condition);
    }

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public SkuReq create(SkuReq skuReq) throws DataDuplicateException, InvalidRequestException {
        this.validate(skuReq);

        SkuReqId id = SkuReqId.builder().coCd(skuReq.getCoCd())
                .cstCd(skuReq.getCstCd())
                .skuCd(skuReq.getSkuCd())
                .bcdNo(skuReq.getBcdNo())
                .build();

        if (skuReqRepository.existsById(id)) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(BCD_NO, String.format("%s + %s + %s %s", accessor.getMessage(LANG_CST_CD),
                    accessor.getMessage(LANG_SKU_CD), accessor.getMessage(LANG_BCD_NO),
                    accessor.getMessage("validation.exist")));
            throw new DataDuplicateException(messageArray);
        }

        LocalDateTime now = LocalDateTime.now();

        skuReq.setRegDt(now);
        skuReq.setUpdDt(now);
        skuReq.setUpdId(skuReq.getRegId());

        return skuReqRepository.save(skuReq);
    }

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public SkuReq update(SkuReq skuReq) throws DataNotFoundException, InvalidRequestException {
        this.validate(skuReq);

        SkuReqId id = SkuReqId.builder().coCd(skuReq.getCoCd())
                .cstCd(skuReq.getCstCd())
                .skuCd(skuReq.getSkuCd())
                .bcdNo(skuReq.getBcdNo())
                .build();

        Optional<SkuReq> byId = skuReqRepository.findById(id);
        if (!byId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(BCD_NO, String.format("%s + %s + %s %s", accessor.getMessage(LANG_CST_CD),
                    accessor.getMessage(LANG_SKU_CD), accessor.getMessage(LANG_BCD_NO),
                    accessor.getMessage(LANG_NOT_FOUND)));
            throw new DataNotFoundException(messageArray);
        }

        SkuReq preSkuReq = byId.get();

        // 변경 사항이 있을 경우 인터페이스 성공 여부를 N 으로
        preSkuReq.setSkuNm(skuReq.getSkuNm());
        preSkuReq.setImageUrl(skuReq.getImageUrl());
        preSkuReq.setSkuStackMethod(skuReq.getSkuStackMethod());
        preSkuReq.setUseYn(skuReq.getUseYn());
        preSkuReq.setBcdType(skuReq.getBcdType());
        preSkuReq.setBcdUseYn(skuReq.getBcdUseYn());
        preSkuReq.setUpdId(skuReq.getRegId());

        preSkuReq.setIfWcsYn("N");

        return skuReqRepository.save(preSkuReq);
    }

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void delete(SkuReq skuReq) throws DataNotFoundException {
        SkuReqId id = SkuReqId.builder().coCd(skuReq.getCoCd())
                .cstCd(skuReq.getCstCd())
                .skuCd(skuReq.getSkuCd())
                .bcdNo(skuReq.getBcdNo())
                .build();

        if (!skuReqRepository.existsById(id)) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(BCD_NO, String.format("%s + %s + %s %s", accessor.getMessage(LANG_CST_CD),
                    accessor.getMessage(LANG_SKU_CD), accessor.getMessage(LANG_BCD_NO),
                    accessor.getMessage(LANG_NOT_FOUND)));
            throw new DataNotFoundException(messageArray);
        }

        skuReqRepository.delete(skuReq);
    }

    private void validate(SkuReq skuReq) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "skuNm", skuReq.getSkuNm(), "column.skuNm", 500, messageArray);
        validator.validateAndThrow(false, "imageUrl", skuReq.getImageUrl(), "column.imageUrl", 200, messageArray);
        validator.validateAndThrow(false, "skuStackMethod", skuReq.getSkuStackMethod(), "column.skuStackMethod", 500, messageArray);
        validator.validateAndThrow(true, "useYn", skuReq.getUseYn(), "column.useYn", 1, messageArray);
        validator.validateAndThrow(false, "bcdType", skuReq.getBcdType(), "column.bcdType", 30, messageArray);
        validator.validateAndThrow(false, "bcdUseYn", skuReq.getBcdUseYn(), "column.bcdUseYn", 1, messageArray);
    }

    public void sendSkuRequest(SkuReqDTO skuReqDTO) {

        //1. Master를 Group by 해서 생성
        List<SkuReqMaster> skuReqMasters = skuReqMapper.getSkuReqMaster(skuReqDTO);

        //2. Detail을 생성
        for (SkuReqMaster skuReqMaster : skuReqMasters) {
            skuReqDTO.setCstCd(skuReqMaster.getCstCd());
            skuReqDTO.setSkuCd(skuReqMaster.getSkuCd());
            skuReqMaster.setDetail(skuReqMapper.getSkuReqBcdMaster(skuReqMaster));
            skuReqDTO.setWmsTrackingId(trackingUidFormat.format(sequence.getWcsTrackingId()));

            //3. Inbound  requestInbound 에 전송
            String json = null;
            boolean isSuccess = true;
            String msg = null;
            InterfaceJsonRequest interfaceJsonRequest = new InterfaceJsonRequest();
            interfaceJsonRequest.setTrackingId(skuReqDTO.getWmsTrackingId());

            interfaceJsonRequest.setData(skuReqMaster);
            // TODO WCS 전송 필요.
            try {
                json = objectMapper.writeValueAsString(interfaceJsonRequest);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
            log.info(json);

            WcsResult wcsResult = wcsClient.callPostUrl(WCSClient.SEND_SKU_MASTER, json);

            updateResult(skuReqDTO, wcsResult.isSuccess(), wcsResult.getMsg().length() < 1000 ? wcsResult.getMsg() : wcsResult.getMsg().substring(0, 999), wcsResult.getCnclCnt());

        }
        log.info("Finished...");

    }

    public void updateResult(SkuReqDTO skuReqDTO, boolean isSuccess, String msg, int cnclCnt) {

        if (isSuccess) {
            skuReqDTO.setIfWcsYn("Y");
            skuReqDTO.setIfWcsStatusCd("OK");
            skuReqDTO.setIfWcsCnt(1);
            skuReqDTO.setIfWcsMsg(CommonConstants.MessageEnum.SUCCESS.getValue());
        } else {
            skuReqDTO.setIfWcsYn("E");
            skuReqDTO.setIfWcsStatusCd("NG");
            skuReqDTO.setIfWcsCnt(cnclCnt);
            skuReqDTO.setIfWcsMsg(msg);

        }
        skuReqMapper.updateWcsSendSkuMaster(skuReqDTO);
    }
}
