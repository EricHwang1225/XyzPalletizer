/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.role.repository;

import com.lgcns.smartwcs.role.model.Role;
import com.lgcns.smartwcs.role.model.RoleSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <PRE>
 * Role Master 검색용 QueryDSL Custom 인터페이스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
public interface RoleRepositoryCustom {
    Page<Role> findAllBySearch(RoleSearchCondition condition, Pageable pageable);

    List<Role> findAllBySearch(RoleSearchCondition condition);
}