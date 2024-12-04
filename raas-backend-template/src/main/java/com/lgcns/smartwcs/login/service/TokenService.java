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

import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.login.model.Token;
import com.lgcns.smartwcs.login.model.UserRole;
import com.lgcns.smartwcs.login.repository.TokenMapper;
import com.lgcns.smartwcs.user.model.User;
import com.lgcns.smartwcs.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

/**
 * <PRE>
 * 토큰 발행 및 조작하기 위한 Service 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j
@Service
public class TokenService {

    public static final Token EMPTY_DATA = new Token();

    @Value("${wcs.sessionTimeOut}")
    private Long sessionTimeOut;

    @Autowired
    UserService userService;

    @Autowired
    TokenMapper tokenMapper;

    public boolean isBlackToken(String id, String jsonWebToken) {

        Token tokenData = Token.builder()
                .tokenId(id)
                .jwTokenString(jsonWebToken)
                .build();

        return tokenMapper.isBlackToken(tokenData);
    }

    public boolean expireTotalToken(String id) {
        log.debug("##### expireTotalToken #####");
        log.info("모든 토큰을 지울 경우");

        Token tokenData = Token.builder()
                .tokenId(id)
                .useYn("N")
                .build();

        tokenMapper.expireTotalToken(tokenData);

        return true;
    }

    public String getJWTokenString(String id) throws DataNotFoundException, NoSuchAlgorithmException {
        log.debug("##### getJWTokenString #####");

        User user = userService.get(id);
        byte[] keyBytes = Decoders.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=");
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Random random = SecureRandom.getInstanceStrong();

        return Jwts.builder()
                .setIssuer(id)
                .setSubject("raas")
                .claim("randomInt", random.nextInt())
                .claim("coCd", user.getCoCd())
                .claim("cntrCd", user.getCntrCd())
                .claim("userLvl", user.getUserLvl())
                .claim("roleCd", user.getRoleCd())
                // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
                .setIssuedAt(Date.from(Instant.now()))
                // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
                .setExpiration(Date.from(Instant.now().plusSeconds(sessionTimeOut)))
                .signWith(key,
                        SignatureAlgorithm.HS256

                )
                .compact();
    }

    /**
     * 토큰 생성 랩퍼     *
     *
     * @param id
     * @return
     */
    public Token createToken(String id) throws DataNotFoundException, NoSuchAlgorithmException {
        log.debug("##### createToken ##### " + id);
        String jwTokenString = getJWTokenString(id);

        Token tokenData = Token.builder()
                .tokenId(id)
                .jwTokenString(jwTokenString)
                .useYn("Y")
                .build();

        tokenMapper.createToken(tokenData);

        log.debug("###### Token Created ######" + id);
        return tokenData;
    }


    public void updateBlackToken(String id, String jsonWebToken) {

        Token tokenData = Token.builder()
                .tokenId(id)
                .jwTokenString(jsonWebToken)
                .build();

        tokenMapper.updateBlackToken(tokenData);

        log.debug("###### Black Token updated ######" + id);
    }

    public boolean isAccessibleMenuByTenant(String requestUri, String coCd) {

        UserRole userRole = UserRole.builder()
                .coCd(coCd)
                .requestUri(requestUri)
                .build();

        return tokenMapper.isAccessibleMenuByTenant(userRole);
    }

    public boolean isAccessibleMenuByCenter(String requestMethod, String requestUri, String coCd, String cntrCd, String roleCd) {

        UserRole userRole = UserRole.builder()
                .coCd(coCd)
                .cntrCd(cntrCd)
                .roleCd(roleCd)
                .requestMethod(requestMethod)
                .requestUri(requestUri)
                .build();

        return tokenMapper.isAccessibleMenuByCenter(userRole);
    }
}
