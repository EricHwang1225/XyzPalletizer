/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.user.repository;

import com.lgcns.smartwcs.user.model.UserDTO;
import com.lgcns.smartwcs.user.model.UserRoleHist;
import com.lgcns.smartwcs.user.model.UserSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * <PRE>
 * Tenant Mapper 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Repository
@Mapper
public interface UserMapper {

    List<UserDTO> getUserList(UserSearchCondition userSearchCondition);

    List<Map<String, Object>> getTenantUserList(UserSearchCondition userSearchCondition);

    List<Map<String, Object>> getCenterUserList(UserSearchCondition userSearchCondition);

    Integer getUserTotalCnt(UserSearchCondition userSearchCondition);

    void saveUserRoleHist(UserRoleHist userRoleHist);
}
