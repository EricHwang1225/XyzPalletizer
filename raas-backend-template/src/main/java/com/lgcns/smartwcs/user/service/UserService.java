/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.user.service;

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.user.model.User;
import com.lgcns.smartwcs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <PRE>
 * 사용자 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    /**
     * 특정 키의 사용자 정보 조회
     *
     * @param userId 사용자의 복합 키
     * @return 조회된 사용자 객체
     * @throws DataNotFoundException 사용자를 찾을 수 없을때 발생
     */
    public User get(String userId) throws DataNotFoundException {
        List<User> userList = userRepository.findAllbyUserId(userId);

        //Login에서 사용하기 위해서 에러 코드를 넣었음. 다른 곳에서 사용하려면 변경해야함
        if (userList.isEmpty()) {
            throw new DataNotFoundException("WRONG_LOGIN_INFO");
        } else if (userList.size() > 1) {
            throw new DataNotFoundException("MULTIPLE_USER_ID");
        }

        User user = userList.get(0);
        if ((user.getUseYn().equals("N")) || !(user.getUserId().equals(userId))) {
            throw new DataNotFoundException("WRONG_LOGIN_INFO");
        }

        return user;
    }


}
