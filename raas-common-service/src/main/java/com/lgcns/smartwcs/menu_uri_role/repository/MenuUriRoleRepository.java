/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.menu_uri_role.repository;

import com.lgcns.smartwcs.menu_uri_role.model.MenuUriRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * Menu Repository 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Repository
public interface MenuUriRoleRepository extends JpaRepository<MenuUriRole, String>, MenuUriRoleRepositoryCustom {
}