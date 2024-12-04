/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.menu_uri_role.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

/**
 * <PRE>
 * Menu Entity 클래스.
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
@Table(name = "TB_COM_METHOD_MST")
public class MenuUriRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "METHOD_UID_KEY")
    private String methodUidKey;

    @Column(name = "MENU_ID")
    private String menuId;

    @Column(name = "METHOD_TYPE")
    private String methodType;


    @Column(name = "URI")
    private String uri;

    @Column(name = "URI_DESC")
    private String uriDesc;

    @Column(name = "USE_YN")
    private String useYn;
}
