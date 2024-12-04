/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.cst.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import com.lgcns.smartwcs.cst.model.ids.CstId;
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
@Table(name = "TB_COM_CST_MST")
@IdClass(CstId.class)
public class Cst extends BaseEntity {

    @Id
    @Column(name = "CO_CD")
    private String coCd;

    @Id
    @Column(name = "CST_CD")
    private String cstCd;

    @Column(name = "CST_NM")
    private String cstNm;

    @Column(name = "ADDR")
    private String addr;

    @Column(name = "TEL_NO")
    private String telNo;

    @Column(name = "USE_YN")
    @ColumnDefault("'Y'")
    private String useYn;
}
