/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.tenant.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import com.lgcns.smartwcs.common.utils.Aes256Converter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;

/**
 * <PRE>
 * Tenant Entity 클래스.
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
@Table(name = "TB_COM_TENANT_MST")
public class Tenant extends BaseEntity {

    @Id
    @Column(name = "CO_CD")
    private String coCd;

    @Column(name = "CO_NM")
    private String coNm;

    @Column(name = "USE_YN")
    @ColumnDefault("'Y'")
    private String useYn;

    @Column(name = "TENANT_ICON")
    private String tenantIcon;

    @Column(name = "ADDR")
    private String addr;

    @Column(name = "TEL_NO")
    private String telNo;

    @Column(name = "TENANT_EMAIL")
    @Convert(converter = Aes256Converter.class)
    private String tenantEmail;

    @Column(name = "CONTR_STRT_YMD")
    private String contrStrtYmd;

    @Column(name = "CONTR_END_YMD")
    private String contrEndYmd;

    @Column(name = "BKGD_COLOR")
    private String bkgdColor;

    @Column(name = "SORT_SEQ")
    private Integer sortSeq;
}
