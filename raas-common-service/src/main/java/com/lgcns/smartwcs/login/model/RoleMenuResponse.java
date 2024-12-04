/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.login.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 로그인 처리 후 롤 메뉴 모델
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuResponse {

    String menuId;
    String langCd;
    String menuNm;
    String menuDesc;
    Integer menuLvl;
    String menuType;
    String menuUrl;
    String menuIcon;
    String roleMenuCd;
    Integer sortSeq;
    String treeId;
    String parentId;

}
