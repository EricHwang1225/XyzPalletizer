/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.tenant.service;

import com.lgcns.smartwcs.center.model.Center;
import com.lgcns.smartwcs.center.model.CenterSearchCondition;
import com.lgcns.smartwcs.center.model.ids.CenterId;
import com.lgcns.smartwcs.center.repository.CenterMapper;
import com.lgcns.smartwcs.center.repository.CenterRepository;
import com.lgcns.smartwcs.common.exception.CommonException;
import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.AES256;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.eqp.model.Eqp;
import com.lgcns.smartwcs.eqp.model.EqpSearchCondition;
import com.lgcns.smartwcs.eqp.model.ids.EqpuipmentId;
import com.lgcns.smartwcs.eqp.repository.EqpMapper;
import com.lgcns.smartwcs.eqp.repository.EqpRepository;
import com.lgcns.smartwcs.port.model.PortSearchCondition;
import com.lgcns.smartwcs.port.repository.PortMapper;
import com.lgcns.smartwcs.tenant.model.Tenant;
import com.lgcns.smartwcs.tenant.model.TenantDetail;
import com.lgcns.smartwcs.tenant.model.TenantSearchCondition;
import com.lgcns.smartwcs.tenant.repository.TenantRepository;
import com.lgcns.smartwcs.tenant.repository.TenantTreeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <PRE>
 * Tenant 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

    private final MessageSourceAccessor accessor;
    private static final String LANG_CO_CD = "column.coCd";
    private static final String LANG_CO_NM = "column.coNm";
    private static final String LANG_CNTR_CD = "column.cntrCd";
    private static final String LANG_CNTR_NM = "column.cntrNm";
    private static final String LANG_EQP_ID = "column.eqpId";
    private static final String LANG_SORT_SEQ = "column.sortSeq";
    private static final String LANG_USE_YN = "column.useYn";
    private static final String LANG_EXIST = "validation.exist";
    private static final String LANG_NOT_FOUND = "validation.notFound";
    private static final String SUPER = "SUPER";
    private static final String TENANT = "TENANT";
    private static final String TENANT_NM = "coNm";
    private static final String CNTR_CD = "cntrCd";
    private static final String CNTR_NM = "cntrNm";
    private static final String EQP_ID = "eqpId";
    private static final String IF_SERV_IP = "ifServIp";
    private static final String SORT_SEQ = "sortSeq";
    private static final String TENANT_EMIAL = "tenantEmail";
    private static final String USE_YN = "useYn";
    private static final String CNTR_EMAIL = "cntrEmail";
    private final AES256 aes256;
    private final TenantRepository tenantRepository;
    private final CenterRepository centerRepository;
    private final EqpRepository eqpRepository;
    private final TenantTreeMapper tenantTreeMapper;
    private final CenterMapper centerMapper;
    private final EqpMapper eqpMapper;
    private final PortMapper portMapper;
    private final Validator validator;


    /**
     * 특정 키의 Tenant 정보 조회
     *
     * @param coCd Tenant의 복합 키
     * @return 조회된 Tenant 객체
     * @throws DataNotFoundException Tenant를 찾을 수 없을때 발생
     */
    public Tenant get(String coCd) throws DataNotFoundException {

        return tenantRepository.findById(coCd)
                .orElseThrow(() -> new DataNotFoundException("Tenant를 찾을 수 없습니다"));
    }


    public List<Map<String, Object>> getTreeList(TenantSearchCondition condition) {

        return tenantTreeMapper.getTenantTreeList(condition);
    }

    public List<Map<String, Object>> getSelectedDetailList(TenantSearchCondition condition) throws CommonException {
        List<Map<String, Object>> selectedList = null;
        switch (condition.getTreeType()) {
            case SUPER:
                condition.setCoCd(null);
                selectedList = tenantTreeMapper.getTenantList(condition);
                this.decryptEmail(selectedList);
                break;
            case TENANT:
                CenterSearchCondition centerSearchCondition = CenterSearchCondition.builder()
                        .coCd(condition.getTreeId()).build();
                selectedList = centerMapper.getCntrList(centerSearchCondition);
                this.decryptEmail(selectedList);
                break;
            case "CNTR":
                String[] splitCntr = condition.getTreeId().split(",");
                EqpSearchCondition eqpSearchCondition = EqpSearchCondition.builder()
                        .coCd(splitCntr[0])
                        .cntrCd(splitCntr[1]).build();
                selectedList = eqpMapper.getEqpList(eqpSearchCondition);
                break;
            case "EQP":
                String[] splitEqp = condition.getTreeId().split(",");
                PortSearchCondition portSearchCondition = PortSearchCondition.builder()
                        .coCd(splitEqp[0])
                        .cntrCd(splitEqp[1])
                        .eqpId(splitEqp[2]).build();
                selectedList = portMapper.getPortList(portSearchCondition);
                break;
            default:
                selectedList = null;
        }
        return selectedList;
    }

    public List<Map<String, Object>> getTenantForUserList(TenantSearchCondition condition) {
        condition.setCoCd(condition.getSearchCoCd());
        return tenantTreeMapper.getTenantForUserList(condition);
    }

    public Map<String, Object> getTenantforMenuList(TenantSearchCondition condition) {

        Map<String, Object> resultMap = new HashMap<>();
        initSearchCondition(condition);

        Integer number = condition.getPage();
        Integer totalElements = 0;
        Integer size = 0;
        Integer totalPages = 0;

        List<Map<String, Object>> content = tenantTreeMapper.getTenantforMenuList(condition);

        if (!content.isEmpty()) {

            totalElements = tenantTreeMapper.getTenantforMenuTotalCnt(condition) - 1;
            size = condition.getSize();
            totalPages = totalElements / size + 1;
        }
        resultMap.put("content", content);
        resultMap.put("totalElements", totalElements);  // 총 count
        resultMap.put("size", size);   // size
        resultMap.put("totalPages", totalPages);   // 총 page 수
        resultMap.put("number", number);  // page 번호 0부터 시작

        return resultMap;
    }

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void createDetail(TenantDetail tenantDetail) throws DataDuplicateException, InvalidRequestException {
        this.validate(tenantDetail);

        LocalDateTime now = LocalDateTime.now();

        switch (tenantDetail.getTreeType()) {
            case SUPER:

                final Optional<Tenant> byId = tenantRepository.findById(tenantDetail.getCoCd());
                if (byId.isPresent()) {
                    HashMap<String, String> messageArray = new HashMap<>();
                    messageArray.put("coCd", accessor.getMessage(LANG_CO_CD) + " : " + tenantDetail.getCoCd() + accessor.getMessage(LANG_EXIST));
                    throw new DataDuplicateException(messageArray);
                }

                final List<Tenant> byTenant = tenantRepository.findAllbyTenantNm(tenantDetail.getCoNm());
                if (!byTenant.isEmpty()) {
                    HashMap<String, String> messageArray = new HashMap<>();
                    messageArray.put(TENANT_NM, accessor.getMessage(LANG_CO_NM) + " : " + tenantDetail.getCoNm() + accessor.getMessage(LANG_EXIST));
                    throw new DataDuplicateException(messageArray);
                }

                Tenant tenant = Tenant.builder()
                        .coCd(tenantDetail.getCoCd())
                        .coNm(tenantDetail.getCoNm())
                        .tenantIcon(tenantDetail.getTenantIcon())
                        .addr(tenantDetail.getAddr())
                        .telNo(tenantDetail.getTelNo())
                        .tenantEmail(tenantDetail.getTenantEmail())
                        .contrStrtYmd(tenantDetail.getContrStrtYmd())
                        .contrEndYmd(tenantDetail.getContrEndYmd())
                        .bkgdColor(tenantDetail.getBkgdColor())
                        .sortSeq(tenantDetail.getSortSeq())
                        .useYn(tenantDetail.getUseYn())
                        .regId(tenantDetail.getRegId())
                        .regDt(now)
                        .updId(tenantDetail.getRegId())
                        .updDt(now).build();
                tenantRepository.save(tenant);
                tenantDetail.setRegDt(tenant.getRegDt().toString());
                break;
            case TENANT:

                checkInvalidCreatingParameterForCenter(tenantDetail);

                Center center = Center.builder()
                        .coCd(tenantDetail.getTreeId())
                        .cntrCd(tenantDetail.getCntrCd())
                        .cntrNm(tenantDetail.getCntrNm())
                        .addr(tenantDetail.getAddr())
                        .telNo(tenantDetail.getTelNo())
                        .sortSeq(tenantDetail.getSortSeq())
                        .cntrEmail(tenantDetail.getCntrEmail())
                        .useYn(tenantDetail.getUseYn())
                        .regId(tenantDetail.getRegId())
                        .regDt(now)
                        .updId(tenantDetail.getRegId())
                        .updDt(now).build();

                centerRepository.save(center);
                tenantDetail.setRegDt(center.getRegDt().toString());
                break;
            case "CNTR":
                String[] splitCntr = tenantDetail.getTreeId().split(",");

                checkInvalidCreatingParameterForEquipment(tenantDetail);

                Eqp eqp = Eqp.builder()
                        .coCd(splitCntr[0])
                        .cntrCd(splitCntr[1])
                        .eqpId(tenantDetail.getEqpId())
                        .eqpNm(tenantDetail.getEqpNm())
                        .appNm(tenantDetail.getAppNm())
                        .ifServIp(tenantDetail.getIfServIp())
                        .eqpDesc(tenantDetail.getEqpDesc())
                        .eqpTypeCd(tenantDetail.getEqpTypeCd())
                        .locCd(tenantDetail.getLocCd())
                        .sortSeq(tenantDetail.getSortSeq())
                        .useYn(tenantDetail.getUseYn())
                        .regId(tenantDetail.getRegId())
                        .regDt(now)
                        .updId(tenantDetail.getRegId())
                        .updDt(now).build();

                eqpRepository.save(eqp);
                tenantDetail.setRegDt(eqp.getRegDt().toString());
                break;
            case "EQP":
            default:
                break;

        }
    }

    private void checkInvalidCreatingParameterForCenter(TenantDetail tenantDetail) throws DataDuplicateException {
        CenterId centerId = CenterId.builder().coCd(tenantDetail.getTreeId()).cntrCd(tenantDetail.getCntrCd()).build();

        final Optional<Center> byCntrId = centerRepository.findById(centerId);
        if (byCntrId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(CNTR_CD, accessor.getMessage(LANG_CNTR_CD) + " : " + tenantDetail.getCntrCd() + accessor.getMessage(LANG_EXIST));
            throw new DataDuplicateException(messageArray);
        }

        final List<Center> byCenter = centerRepository.findAllbyCenterNm(tenantDetail.getTreeId(), tenantDetail.getCntrNm());
        if (!byCenter.isEmpty()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(CNTR_NM, accessor.getMessage(LANG_CNTR_NM) + " : " + tenantDetail.getCntrNm() + accessor.getMessage(LANG_EXIST));
            throw new DataDuplicateException(messageArray);
        }
    }

    private void checkInvalidCreatingParameterForEquipment(TenantDetail tenantDetail) throws DataDuplicateException {
        String[] splitCntr = tenantDetail.getTreeId().split(",");

        EqpuipmentId eqpuipmentId = EqpuipmentId.builder().coCd(splitCntr[0]).cntrCd(splitCntr[1]).eqpId(tenantDetail.getEqpId()).build();

        final Optional<Eqp> byEqpId = eqpRepository.findById(eqpuipmentId);
        if (byEqpId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(EQP_ID, accessor.getMessage(LANG_EQP_ID) + " : " + tenantDetail.getEqpId() + accessor.getMessage(LANG_EXIST));
            throw new DataDuplicateException(messageArray);
        }

        if (tenantDetail.getIfServIp() != null && !tenantDetail.getIfServIp().isEmpty()) {
            EqpSearchCondition eqpSearchCondition = EqpSearchCondition.builder()
                    .ifServIp(tenantDetail.getIfServIp())
                    .build();
            if (!eqpRepository.findAllBySearch(eqpSearchCondition).isEmpty()) {
                HashMap<String, String> messageArray = new HashMap<>();
                messageArray.put(IF_SERV_IP, accessor.getMessage("column.autostoreIP") + " : " + tenantDetail.getIfServIp() + accessor.getMessage(LANG_EXIST));
                throw new DataDuplicateException(messageArray);
            }

        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public void selectedDetailupdate(TenantDetail tenantDetail) throws DataNotFoundException, InvalidRequestException, DataDuplicateException {
        this.validate(tenantDetail);

        LocalDateTime now = LocalDateTime.now();

        switch (tenantDetail.getTreeType()) {
            case SUPER:

                final Optional<Tenant> byId = tenantRepository.findById(tenantDetail.getCoCd());
                if (!byId.isPresent()) {
                    HashMap<String, String> messageArray = new HashMap<>();
                    messageArray.put("coCd", accessor.getMessage(LANG_CO_CD) + " : " + tenantDetail.getCoCd() + accessor.getMessage(LANG_NOT_FOUND));
                    throw new DataNotFoundException(messageArray);
                }


                final List<Tenant> byTenant = tenantRepository.findAllbyTenantNm(tenantDetail.getCoNm());
                if (byTenant.size() > 1 || (byTenant.size() == 1 && !byTenant.get(0).getCoCd().equals(tenantDetail.getCoCd()))) {
                    HashMap<String, String> messageArray = new HashMap<>();
                    messageArray.put(TENANT_NM, accessor.getMessage(LANG_CO_NM) + " : " + tenantDetail.getCoNm() + accessor.getMessage(LANG_EXIST));
                    throw new DataDuplicateException(messageArray);
                }

                Tenant prevTenant = byId.get();

                // 변경가능한 항목만 변경
                prevTenant.setCoNm(tenantDetail.getCoNm());
                prevTenant.setTenantIcon(tenantDetail.getTenantIcon());
                prevTenant.setUseYn(tenantDetail.getUseYn());
                prevTenant.setAddr(tenantDetail.getAddr());
                prevTenant.setTelNo(tenantDetail.getTelNo());
                prevTenant.setTenantEmail(tenantDetail.getTenantEmail());
                prevTenant.setContrStrtYmd(tenantDetail.getContrStrtYmd());
                prevTenant.setContrEndYmd(tenantDetail.getContrEndYmd());
                prevTenant.setBkgdColor(tenantDetail.getBkgdColor());
                prevTenant.setSortSeq(tenantDetail.getSortSeq());

                prevTenant.setUpdDt(now);
                prevTenant.setUpdId(tenantDetail.getRegId());

                tenantRepository.save(prevTenant);
                break;
            case TENANT:

                Center prevCenter = checkInvalidParameterForCenter(tenantDetail).get();

                // 변경가능한 항목만 변경
                prevCenter.setCntrNm(tenantDetail.getCntrNm());
                prevCenter.setUseYn(tenantDetail.getUseYn());
                prevCenter.setAddr(tenantDetail.getAddr());
                prevCenter.setTelNo(tenantDetail.getTelNo());
                prevCenter.setSortSeq(tenantDetail.getSortSeq());
                prevCenter.setCntrEmail(tenantDetail.getCntrEmail());

                prevCenter.setUpdDt(now);
                prevCenter.setUpdId(tenantDetail.getRegId());

                centerRepository.save(prevCenter);
                break;
            case "CNTR":

                Eqp prevEqp = checkInvalidParameterForEquipment(tenantDetail).get();

                // 변경가능한 항목만 변경
                prevEqp.setEqpNm(tenantDetail.getEqpNm());
                prevEqp.setUseYn(tenantDetail.getUseYn());
                prevEqp.setIfServIp(tenantDetail.getIfServIp());
                prevEqp.setLocCd(tenantDetail.getLocCd());
                prevEqp.setEqpDesc(tenantDetail.getEqpDesc());
                prevEqp.setEqpTypeCd(tenantDetail.getEqpTypeCd());
                prevEqp.setSortSeq(tenantDetail.getSortSeq());
                prevEqp.setAppNm(tenantDetail.getAppNm());

                prevEqp.setUpdDt(now);
                prevEqp.setUpdId(tenantDetail.getRegId());

                eqpRepository.save(prevEqp);
                break;
            case "EQP":
            default:
                break;

        }
    }

    private Optional<Center> checkInvalidParameterForCenter(TenantDetail tenantDetail) throws DataNotFoundException, DataDuplicateException {
        CenterId centerId = CenterId.builder().coCd(tenantDetail.getTreeId()).cntrCd(tenantDetail.getCntrCd()).build();

        final Optional<Center> byCenterId = centerRepository.findById(centerId);
        if (!byCenterId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(CNTR_CD, accessor.getMessage(LANG_CNTR_CD) + " : " + tenantDetail.getCntrCd() + accessor.getMessage(LANG_NOT_FOUND));
            throw new DataNotFoundException(messageArray);
        }

        List<Center> byCenter = centerRepository.findAllbyCenterNm(tenantDetail.getTreeId(), tenantDetail.getCntrNm());
        if (byCenter.size() > 1 || (byCenter.size() == 1 && !byCenter.get(0).getCntrCd().equals(tenantDetail.getCntrCd()))) {

            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(CNTR_NM, accessor.getMessage(LANG_CNTR_NM) + " : " + tenantDetail.getCntrNm() + accessor.getMessage(LANG_EXIST));
            throw new DataDuplicateException(messageArray);
        }

        return byCenterId;
    }

    private Optional<Eqp> checkInvalidParameterForEquipment(TenantDetail tenantDetail) throws DataNotFoundException, DataDuplicateException {
        String[] splitCntr = tenantDetail.getTreeId().split(",");

        EqpuipmentId eqpuipmentId = EqpuipmentId.builder().coCd(splitCntr[0]).cntrCd(splitCntr[1]).eqpId(tenantDetail.getEqpId()).build();

        final Optional<Eqp> byEqpId = eqpRepository.findById(eqpuipmentId);
        if (!byEqpId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(EQP_ID, accessor.getMessage(LANG_EQP_ID) + " : " + tenantDetail.getEqpId() + accessor.getMessage(LANG_NOT_FOUND));
            throw new DataNotFoundException(messageArray);
        }

        if (StringUtils.hasText(tenantDetail.getIfServIp()) && (StringUtils.hasText(byEqpId.get().getIfServIp()) && !byEqpId.get().getIfServIp().equals(tenantDetail.getIfServIp()))
                || StringUtils.hasText(tenantDetail.getIfServIp()) && !StringUtils.hasText(byEqpId.get().getIfServIp())) {

            EqpSearchCondition eqpSearchCondition = EqpSearchCondition.builder()
                    .ifServIp(tenantDetail.getIfServIp())
                    .build();
            if (!eqpRepository.findAllBySearch(eqpSearchCondition).isEmpty()) {
                HashMap<String, String> messageArray = new HashMap<>();
                messageArray.put(IF_SERV_IP, accessor.getMessage("column.autostoreIP") + " : " + tenantDetail.getIfServIp() + accessor.getMessage(LANG_EXIST));
                throw new DataDuplicateException(messageArray);
            }
        }

        return byEqpId;
    }

    private void validate(TenantDetail detail) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "treeId", detail.getTreeId(), "column.treeId", -1, messageArray);
        validator.validateAndThrow(true, "treeType", detail.getTreeType(), "column.treeType", -1, messageArray);

        switch (detail.getTreeType()) {
            case SUPER:
                validator.validateAndThrow(true, "coCd", detail.getCoCd(), LANG_CO_CD, 30, messageArray);
                validator.validateCdAndThrow("coCd", detail.getCoCd(), LANG_CO_CD, messageArray);
                validator.validateAndThrow(true, "coNm", detail.getCoNm(), LANG_CO_NM, 50, messageArray);
                validator.validateAndThrow(true, SORT_SEQ, detail.getSortSeq(), LANG_SORT_SEQ, 9, messageArray);
                validator.validateAndThrow(true, TENANT_EMIAL, detail.getTenantEmail(), "column.tenantEmail", 100, messageArray);
                validator.validateAndThrow(false, USE_YN, detail.getUseYn(), LANG_USE_YN, 1, messageArray);
                validator.validateAndThrow(false, "contrStrtYmd", detail.getContrStrtYmd(), "column.contrStrtYmd", 8, messageArray);
                validator.validateAndThrow(false, "contrEndYmd", detail.getContrEndYmd(), "column.contrEndYmd", 8, messageArray);
                break;
            case TENANT:
                validator.validateAndThrow(true, CNTR_CD, detail.getCntrCd(), LANG_CNTR_CD, 30, messageArray);
                validator.validateCdAndThrow(CNTR_CD, detail.getCntrCd(), LANG_CNTR_CD, messageArray);
                validator.validateAndThrow(true, CNTR_NM, detail.getCntrNm(), LANG_CNTR_NM, 50, messageArray);
                validator.validateAndThrow(true, SORT_SEQ, detail.getSortSeq(), LANG_SORT_SEQ, 9, messageArray);
                validator.validateAndThrow(true, CNTR_EMAIL, detail.getCntrEmail(), "column.cntrEmail", 100, messageArray);
                validator.validateAndThrow(false, USE_YN, detail.getUseYn(), LANG_USE_YN, 1, messageArray);
                break;
            case "CNTR":
                validator.validateAndThrow(true, EQP_ID, detail.getEqpId(), LANG_EQP_ID, 30, messageArray);
                validator.validateAndThrow(true, "eqpNm", detail.getEqpNm(), "column.eqpNm", 50, messageArray);
                validator.validateAndThrow(true, SORT_SEQ, detail.getSortSeq(), LANG_SORT_SEQ, 9, messageArray);
                validator.validateAndThrow(false, USE_YN, detail.getUseYn(), LANG_USE_YN, 1, messageArray);
                validator.validateAndThrow(false, IF_SERV_IP, detail.getIfServIp(), "column.ifServIp", 100, messageArray);
                validator.validateAndThrow(false, "eqpDesc", detail.getEqpDesc(), "column.eqpDesc", 300, messageArray);
                validator.validateAndThrow(false, "locCd", detail.getLocCd(), "column.locCd", 30, messageArray);
                break;
            default:
                break;
        }

        validator.validateAndThrow(true, "regId", detail.getRegId(), "column.regId", 30, messageArray);
    }

    private void initSearchCondition(TenantSearchCondition condition) {
        condition.setCoCd(condition.getSearchCoCd());

        if (condition.getPage() == null)
            condition.setPage(0);

        if (condition.getPage() != null && condition.getSize() != null) {
            condition.setPage(condition.getSize() * condition.getPage());
        }
    }

    private void decryptEmail(List<Map<String, Object>> selectedList) throws CommonException {
        for (Map<String, Object> selectedItem : selectedList) {
            String encryptedTenantEmail = (String) selectedItem.get(TENANT_EMIAL);
            if (StringUtils.hasText(encryptedTenantEmail)) {
                try {
                    String decryptedEmail = aes256.decrypt(encryptedTenantEmail);
                    selectedItem.replace(TENANT_EMIAL, decryptedEmail);
                } catch (Exception e) {
                    throw new CommonException("email decrypt error");
                }
            }

            String encryptedCntrEmail = (String) selectedItem.get(CNTR_EMAIL);
            if (StringUtils.hasText(encryptedCntrEmail)) {
                try {
                    String decryptedEmail = aes256.decrypt(encryptedCntrEmail);
                    selectedItem.replace(CNTR_EMAIL, decryptedEmail);
                } catch (Exception e) {
                    throw new CommonException("email decrypt error");
                }
            }
        }
    }
}
