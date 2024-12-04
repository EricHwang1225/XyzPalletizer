package com.lgcns.smartwcs.req.sku.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import com.lgcns.smartwcs.req.sku.model.ids.SkuReqId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_REQ_SKU_MST")
@IdClass(SkuReqId.class)
@DynamicInsert
@DynamicUpdate
public class SkuReq extends BaseEntity {
    @Id
    @Column(name = "CO_CD")
    private String coCd;

    @Id
    @Column(name = "CST_CD")
    private String cstCd;

    @Id
    @Column(name = "SKU_CD")
    private String skuCd;

    @Id
    @Column(name = "BCD_NO")
    private String bcdNo;

    @Column(name = "SKU_NM")
    private String skuNm;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "SKU_STACK_METHOD")
    private String skuStackMethod;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "BCD_TYPE")
    private String bcdType;

    @Column(name = "BCD_USE_YN")
    private String bcdUseYn;

    @Column(name = "IF_WCS_YN")
    private String ifWcsYn;

    @Column(name = "IF_WCS_STATUS_CD")
    private String ifWcsStatusCd;

    @Column(name = "IF_WCS_CNT")
    private Integer ifWcsCnt;

    @Column(name = "IF_WCS_DT")
    private String ifWcsDt;

    @Column(name = "IF_WCS_MSG")
    private String ifWcsMsg;

    @Column(name = "WMS_TRACKING_ID")
    private String wmsTrackingId;

    @Column(name = "WCS_REG_YN")
    private String wcsRegYn;
}
