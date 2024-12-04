/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.role.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import com.lgcns.smartwcs.role.model.ids.RoleId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

/**
 * <PRE>
 * 권한 Entity 클래스.
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
@Table(name = "TB_COM_ROLE_MST")
@IdClass(RoleId.class)
public class Role extends BaseEntity {

    @Id
    @Column(name = "CO_CD")
    private String coCd;

    @Id
    @Column(name = "CNTR_CD")
    private String cntrCd;

    @Id
    @Column(name = "ROLE_CD")
    private String roleCd;

    @Column(name = "ROLE_NM")
    private String roleNm;

    @Column(name = "USE_YN")
    private String useYn;
}
