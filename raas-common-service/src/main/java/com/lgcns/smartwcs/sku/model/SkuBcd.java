package com.lgcns.smartwcs.sku.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import com.lgcns.smartwcs.sku.model.ids.SkuBcdId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

/**
 * <PRE>
 * Sku  Entity 클래스.
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
@Table(name = "TB_COM_SKU_BCD_MST")
@IdClass(SkuBcdId.class)
public class SkuBcd extends BaseEntity {

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
    @Column(name = "BCD")
    private String bcd;

    @Column(name = "BCD_TYPE")
    private String bcdType;

    @Column(name = "USE_YN")
    private String useYn;
}
