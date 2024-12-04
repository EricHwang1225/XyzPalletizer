/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.code.model;

import com.lgcns.smartwcs.code.model.ids.CodeId;
import com.lgcns.smartwcs.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;

/**
 * <PRE>
 * 공통 코드 Entity 클래스.
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
@Table(name = "TB_COM_CODE_MST")
@IdClass(CodeId.class)
public class Code extends BaseEntity {

    @Id
    @Column(name = "CO_CD")
    private String coCd;

    @Id
    @Column(name = "COM_HDR_CD")
    private String comHdrCd;

    @Id
    @Column(name = "COM_DTL_CD")
    private String comDtlCd;

    @Column(name = "COM_HDR_NM")
    private String comHdrNm;

    @Column(name = "COM_DTL_NM")
    private String comDtlNm;

    @Column(name = "USE_YN")
    @ColumnDefault("'Y'")
    private String useYn;

    @Column(name = "HDR_FLAG")
    private String hdrFlag;

    @Column(name = "SORT_SEQ")
    private Integer sortSeq;
}
