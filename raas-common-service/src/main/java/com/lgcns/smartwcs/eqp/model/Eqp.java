/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.eqp.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import com.lgcns.smartwcs.eqp.model.ids.EqpuipmentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;

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
@Table(name = "TB_COM_EQP_MST")
@IdClass(EqpuipmentId.class)
public class Eqp extends BaseEntity {

    @Id
    @Column(name = "CO_CD")
    private String coCd;

    @Id
    @Column(name = "CNTR_CD")
    private String cntrCd;

    @Id
    @Column(name = "EQP_ID")
    private String eqpId;

    @Column(name = "EQP_NM")
    private String eqpNm;

    @Column(name = "USE_YN")
    @ColumnDefault("'Y'")
    private String useYn;

    @Column(name = "SORT_SEQ")
    private Integer sortSeq;

    @Column(name = "EQP_DESC")
    private String eqpDesc;

    @Column(name = "EQP_TYPE_CD")
    private String eqpTypeCd;

    @Column(name = "LOC_CD")
    private String locCd;

    @Column(name = "IF_SERV_IP")
    private String ifServIp;

    @Column(name = "TOTE_REGEX")
    private String toteRegex;

    @Column(name = "TOTE_UNAVBL_MIN")
    private Integer toteUnavblMin;

    @Column(name = "APP_NM")
    private String appNm;
}
