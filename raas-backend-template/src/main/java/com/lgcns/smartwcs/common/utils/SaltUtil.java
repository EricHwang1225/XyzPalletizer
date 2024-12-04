/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * <PRE>
 * 사용자 비밀번호를 암호화 하기 위한 BCrypt 암호화 유틸리티 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j
@Service
public class SaltUtil {
    public String encryptPassword(String source, String salt) {

        String result = "";

        if(salt == null) {
            return result;
        }

        byte[] byteSalts = salt.getBytes();
        byte[] a = source.getBytes();
        byte[] bytes = new byte[a.length + byteSalts.length];

        System.arraycopy(a, 0, bytes, 0, a.length);
        System.arraycopy(byteSalts, 0, bytes, a.length, byteSalts.length);

        try {
            // 암호화 방식 지정 메소드
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes);

            byte[] byteData = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
            }

            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }

        return result;
    }

    /**
     * SALT를 얻어온다.
     * @return
     */
    public String generateSalt() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();

        byte[] salt = new byte[8];
        random.nextBytes(salt);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < salt.length; i++) {
            // byte 값을 Hex 값으로 바꾸기.
            sb.append(String.format("%02x",salt[i]));
        }

        return sb.toString();
    }

    public boolean validatePassword(String password, String salt, String encryptedPassword) {
        String encryptedResult = encryptPassword(password, salt);
        return encryptedResult.equals(encryptedPassword);
    }
}
