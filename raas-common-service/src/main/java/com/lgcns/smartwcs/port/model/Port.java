/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.port.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import com.lgcns.smartwcs.port.model.ids.PortPkId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_COM_PORT_MST")
@IdClass(PortPkId.class)
public class Port extends BaseEntity {

    @Id
    @Column(name = "CO_CD")
    private String coCd;

    @Id
    @Column(name = "CNTR_CD")
    private String cntrCd;

    @Id
    @Column(name = "EQP_ID")
    private String eqpId;

    @Id
    @Column(name = "PORT_ID")
    private String portId;

    @Column(name = "PORT_NM")
    private String portNm;

    @Column(name = "USE_YN")
    @ColumnDefault("'Y'")
    private String useYn;

    @Column(name = "SORT_SEQ")
    private Integer sortSeq;

    @Column(name = "PORT_DESC")
    private String portDesc;

    @Column(name = "PC_IP")
    private String pcIp;

    @Column(name = "LIGHT_IP")
    private String lightIp;

    @Column(name = "LIGHT_USE_YN")
    @ColumnDefault("'Y'")
    private String lightUseYn;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "WR_IN_ID")
    private String wrInId;

    @Column(name = "WR_OUT_ID")
    private String wrOutId;

    @Column(name = "PORT_STRT_YN")
    @ColumnDefault("'Y'")
    private String portStrtYn;


}
