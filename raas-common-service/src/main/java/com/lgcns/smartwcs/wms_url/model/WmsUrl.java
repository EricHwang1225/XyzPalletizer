/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.wms_url.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import com.lgcns.smartwcs.wms_url.model.ids.WmsUrlId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_COM_WMS_URL")
@IdClass(WmsUrlId.class)
public class WmsUrl extends BaseEntity {

    @Id
    @Column(name = "CO_CD")
    private String coCd;

    @Id
    @Column(name = "WMS_IF_ID")
    private String wmsIfId;

    @Column(name = "WMS_IF_URL")
    private String wmsIfUrl;

    private String wmsIfNm;
}
