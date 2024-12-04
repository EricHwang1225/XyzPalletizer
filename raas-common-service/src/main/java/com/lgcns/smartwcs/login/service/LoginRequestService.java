/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.login.service;

import com.lgcns.smartwcs.common.utils.SaltUtil;
import com.lgcns.smartwcs.login.model.Login;
import com.lgcns.smartwcs.login.model.LoginAccess;
import com.lgcns.smartwcs.login.model.RoleMenuResponse;
import com.lgcns.smartwcs.login.repository.LoginRequestMapper;
import com.lgcns.smartwcs.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <PRE>
 * 로그인 요청을 조작하기 위한 Service 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j
@Service
public class LoginRequestService {

    @Autowired
    private LoginRequestMapper loginRequestMapper;

    /**
     * 암호화 유틸리티 객체
     */
    @Autowired
    private SaltUtil saltUtil;


    public Integer updateLoginFailCnt(User userModel) {
        return loginRequestMapper.updateLoginFailCnt(userModel);
    }

    public Integer updatePasswordChangeComplete(User userModel) {
        return loginRequestMapper.updatePasswordChangeComplete(userModel);
    }

    public Integer updateLoginComplete(User userModel) {
        return loginRequestMapper.updateLoginComplete(userModel);
    }

    public Integer updateLogoutComplete(User userModel) {
        return loginRequestMapper.updateLogoutComplete(userModel);
    }

    public Integer insertLoginAccessLog(LoginAccess loginAccess) {
        return loginRequestMapper.insertLoginAccessLog(loginAccess);
    }

    public List<RoleMenuResponse> getUserRoleMenuLvl1(Login login) {
        return loginRequestMapper.getUserRoleMenuLvl1(login);
    }

    public List<RoleMenuResponse> getUserRoleMenuLvl2(Login login) {
        return loginRequestMapper.getUserRoleMenuLvl2(login);
    }

    public List<RoleMenuResponse> getUserRoleMenuLvl3(Login login) {
        return loginRequestMapper.getUserRoleMenuLvl3(login);
    }
}
