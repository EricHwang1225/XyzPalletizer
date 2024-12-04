/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.repository;

import com.lgcns.smartwcs.model.Token;
import com.lgcns.smartwcs.model.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
public interface TokenMapper {

    void createToken(Token tokenData);

    void expireTotalToken(Token tokenData);

    boolean isBlackToken(Token tokenData);

    void updateBlackToken(Token tokenData);

    boolean isAccessibleMenuByTenant(UserRole userRole);

    boolean isAccessibleMenuByCenter(UserRole userRole);
}
