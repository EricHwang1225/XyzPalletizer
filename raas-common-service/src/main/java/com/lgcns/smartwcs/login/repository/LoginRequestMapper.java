/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.login.repository;

import com.lgcns.smartwcs.login.model.Login;
import com.lgcns.smartwcs.login.model.LoginAccess;
import com.lgcns.smartwcs.login.model.LoginResponse;
import com.lgcns.smartwcs.login.model.RoleMenuResponse;
import com.lgcns.smartwcs.port.model.Port;
import com.lgcns.smartwcs.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 로그인 요청정보 Mapper 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Repository
@Mapper
public interface LoginRequestMapper {

    LoginResponse getLogin(User userModel);

    Integer updateLoginFailCnt(User userModel);

    Integer updatePasswordChangeComplete(User userModel);

    Integer updateLoginComplete(User userModel);

    Integer updateLogoutComplete(User userModel);

    Integer insertLoginAccessLog(LoginAccess loginAccess);

    List<RoleMenuResponse> getUserRoleMenuLvl1(Login login);

    List<RoleMenuResponse> getUserRoleMenuLvl2(Login login);

    List<RoleMenuResponse> getUserRoleMenuLvl3(Login login);

    Map<String, Object> getEqpPortId(Port port);
}
