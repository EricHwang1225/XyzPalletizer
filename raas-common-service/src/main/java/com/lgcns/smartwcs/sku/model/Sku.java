/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.sku.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import com.lgcns.smartwcs.sku.model.ids.SkuId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * <PRE>
 * 사용자 Entity 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_COM_SKU_MST")
@IdClass(SkuId.class)
public class Sku extends BaseEntity {

    @Id
    @Column(name = "CO_CD")
    @Schema(name = "회사 코드")
    private String coCd;

    @Id
    @Column(name = "CST_CD")
    @Schema(name = "화주 코드")
    private String cstCd;

    @Id
    @Column(name = "SKU_CD")
    @Schema(name = "상품 코드")
    private String skuCd;

    @Column(name = "SKU_NM")
    @Schema(name = "상품 명")
    private String skuNm;

    @Column(name = "SKU_CNTR_NM")
    @Schema(name = "센터 상품 명")
    private String skuCntrNm;

    @Column(name = "UOM_CD")
    @Schema(name = "U0M 단위")
    private String uomCd;

    @Column(name = "UOM_QTY")
    @Schema(name = "Units Per Measure")
    private BigDecimal uomQty;

    @Column(name = "WIDTH")
    @Schema(name = "가로(SKU)")
    private BigDecimal width;

    @Column(name = "LENGTH")
    @Schema(name = "세로(SKU)")
    private BigDecimal length;

    @Column(name = "HEIGHT")
    @Schema(name = "높이(SKU)")
    private BigDecimal height;

    @Column(name = "CBM")
    @Schema(name = "CBM (부피 cm**3)")
    private BigDecimal cbm;

    @Column(name = "GROSS_WEIGHT")
    @Schema(name = "Sku Gross Weight")
    private BigDecimal grossWeight;

    @Column(name = "NET_WEIGHT")
    @Schema(name = "Sku Net Weight")
    private BigDecimal netWeight;

    @Column(name = "BOX_GROSS_WEIGHT")
    @Schema(name = "Box Gross Weight")
    private BigDecimal boxGrossWeight;

    @Column(name = "BOX_NET_WEIGHT")
    @Schema(name = "Box Net Weight")
    private BigDecimal boxNetWeight;

    @Column(name = "UOM_LEN")
    @Schema(name = "길이 단위")
    private String uomLen;

    @Column(name = "UOM_CBM")
    @Schema(name = "CBM 단위")
    private String uomCbm;

    @Column(name = "UOM_WEIGHT")
    @Schema(name = "중량 단위")
    private String uomWeight;

    @Column(name = "SKU_GRP_01")
    @Schema(name = "SKU Group 01(상품그룹)")
    private String skuGrp01;

    @Column(name = "SKU_GRP_02")
    @Schema(name = "SKU Group 02(상품그룹)")
    private String skuGrp02;

    @Column(name = "BOX_IN_QTY")
    @Schema(name = "박스당 상품 수량")
    private Integer boxInQty;

    @Column(name = "UNUSUAL_YN")
    @Schema(name = "이형 상품")
    private String unusualYn;

    @Column(name = "PROD_CD")
    @Schema(name = "출하 상품 코드")
    private String prodCd;

    @Column(name = "IMAGE_URL")
    @Schema(name = "상품 이미지 URL 정보")
    private String imageUrl;

    @Column(name = "SKU_STACK_METHOD")
    @Schema(name = "상품 이미지 URL 정보")
    private String skuStackMethod;

    @Column(name = "USE_YN")
    @ColumnDefault("'Y'")
    @Schema(name = "사용 여부")
    private String useYn;
}
