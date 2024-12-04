package com.lgcns.smartwcs.req.cst.service;

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
import com.lgcns.smartwcs.req.cst.model.CstReq;
import com.lgcns.smartwcs.req.cst.model.CstReqDTO;
import com.lgcns.smartwcs.req.cst.model.ids.CstReqId;
import com.lgcns.smartwcs.req.cst.repository.CstReqMapper;
import com.lgcns.smartwcs.req.cst.repository.CstReqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CstReqService {


    private final Sequence sequence;

    private DecimalFormat trackingUidFormat = new DecimalFormat("WMS00000000000");

    private ObjectMapper objectMapper = new ObjectMapper();

    private final WCSClient wcsClient;

    private static final String CST_CD = "cstCd";
    private static final String LANG_CST_CD = "column.cstCd";
    private static final String LANG_NOT_FOUND = "validation.notFound";

    private final CstReqMapper cstReqMapper;
    private final CstReqRepository cstReqRepository;
    private final MessageSourceAccessor accessor;
    private final Validator validator;

    public List<CstReqDTO> getUnpagedList(CstReqDTO condition) {
        return cstReqMapper.getCstReqList(condition);
    }

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public CstReq create(CstReq cstReq) throws DataDuplicateException, InvalidRequestException {
        this.validate(cstReq);

        CstReqId id = CstReqId.builder()
                .coCd(cstReq.getCoCd())
                .cstCd(cstReq.getCstCd())
                .build();

        if (cstReqRepository.existsById(id)) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(CST_CD, accessor.getMessage(LANG_CST_CD) + " " + accessor.getMessage("validation.exist"));
            throw new DataDuplicateException(messageArray);
        }

        LocalDateTime now = LocalDateTime.now();

        cstReq.setRegDt(now);
        cstReq.setUpdDt(now);
        cstReq.setUpdId(cstReq.getRegId());

        return cstReqRepository.save(cstReq);
    }

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public CstReq update(CstReq cstReq) throws DataNotFoundException, InvalidRequestException {
        this.validate(cstReq);

        CstReqId id = CstReqId.builder()
                .coCd(cstReq.getCoCd())
                .cstCd(cstReq.getCstCd())
                .build();

        Optional<CstReq> byId = cstReqRepository.findById(id);
        if (!byId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(CST_CD, accessor.getMessage(LANG_CST_CD) + " " + accessor.getMessage(LANG_NOT_FOUND));
            throw new DataNotFoundException(messageArray);
        }

        CstReq preCstReq = byId.get();

        // 변경 사항이 있을 경우 인터페이스 성공 여부를 N 으로
        if (!preCstReq.getCstNm().equals(cstReq.getCstNm()) || !preCstReq.getUseYn().equals(cstReq.getUseYn())) {
            preCstReq.setCstNm(cstReq.getCstNm());
            preCstReq.setUseYn(cstReq.getUseYn());
            preCstReq.setUpdId(cstReq.getRegId());
            preCstReq.setIfWcsYn("N");
            return cstReqRepository.save(preCstReq);
        } else {
            return preCstReq;
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void delete(CstReq cstReq) throws DataNotFoundException {
        CstReqId id = CstReqId.builder()
                .coCd(cstReq.getCoCd())
                .cstCd(cstReq.getCstCd())
                .build();

        if (!cstReqRepository.existsById(id)) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(CST_CD, accessor.getMessage(LANG_CST_CD) + " " + accessor.getMessage(LANG_NOT_FOUND));
            throw new DataNotFoundException(messageArray);
        }

        cstReqRepository.delete(cstReq);
    }

    private void validate(CstReq cstReq) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "cstCd", cstReq.getCstCd(), "column.cstCd", 100, messageArray);
        validator.validateAndThrow(true, "cstNm", cstReq.getCstNm(), "column.cstNm", 100, messageArray);
        validator.validateAndThrow(false, "useYn", cstReq.getUseYn(), "column.useYn", 100, messageArray);
    }

    public void sendOwnerMasterInfo(CstReqDTO cstReqDTO) {

        List<CstReqDTO> cstReqDTOs = cstReqMapper.getCstReqList(cstReqDTO);

        Date currentDate = Date.from(Instant.now());
        LocalDateTime localDateTime = currentDate.toInstant() // Date -> Instant
                .atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
                .toLocalDateTime();

        for (CstReqDTO cstReq : cstReqDTOs) {

            cstReq.setWmsTrackingId(trackingUidFormat.format(sequence.getWcsTrackingId()));
            cstReq.setRegDt(localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

            //1. sendOwnerMasterInfo 에 전송
            String json = null;
            boolean isSuccess = true;
            String msg = null;
            InterfaceJsonRequest interfaceJsonRequest = new InterfaceJsonRequest();
            interfaceJsonRequest.setTrackingId(cstReq.getWmsTrackingId());

            interfaceJsonRequest.setData(cstReq);

            try {
                json = objectMapper.writeValueAsString(interfaceJsonRequest);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
            log.info(json);

            WcsResult wcsResult = wcsClient.callPostUrl(WCSClient.SEND_OWNER_MASTER, json);

            updateResult(cstReq, wcsResult.isSuccess(), wcsResult.getMsg().length() < 1000 ? wcsResult.getMsg() : wcsResult.getMsg().substring(0, 999), wcsResult.getCnclCnt());

        }
        log.info("Finished...");
    }

    public void updateResult(CstReqDTO cstReqDTO, boolean isSuccess, String msg, int cnclCnt) {

        if (isSuccess) {
            cstReqDTO.setIfWcsYn("Y");
            cstReqDTO.setIfWcsStatusCd("OK");
            cstReqDTO.setIfWcsCnt(1);
            cstReqDTO.setIfWcsMsg(CommonConstants.MessageEnum.SUCCESS.getValue());
        } else {
            cstReqDTO.setIfWcsYn("E");
            cstReqDTO.setIfWcsStatusCd("NG");
            cstReqDTO.setIfWcsCnt(cnclCnt);
            cstReqDTO.setIfWcsMsg(msg);

        }
        cstReqMapper.updateWcsCstRequest(cstReqDTO);
    }
}
