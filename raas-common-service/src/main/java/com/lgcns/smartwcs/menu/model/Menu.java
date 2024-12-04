/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.menu.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
@Table(name = "TB_COM_MENU_MST")
public class Menu extends BaseEntity {

    @Id
    @Column(name = "MENU_ID")
    private String menuId;

    @Column(name = "MENU_NM")
    private String menuNm;

    @Column(name = "MENU_DESC")
    private String menuDesc;

    @Column(name = "MENU_LVL")
    private Integer menuLvl;

    @Column(name = "MENU_TYPE")
    private String menuType;

    @Column(name = "MENU_URL")
    private String menuUrl;

    @Column(name = "MENU_ICON")
    private String menuIcon;

    @Column(name = "TREE_ID")
    private String treeId;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "SORT_SEQ")
    private Integer sortSeq;

    @Column(name = "MAIN_SHOW_ICON")
    private String mainShowIcon;

    @Column(name = "USER_LVL")
    private Integer userLvl;

    @Column(name = "USE_YN")
    private String useYn;
}
