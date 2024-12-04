/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.service;



import com.lgcns.smartwcs.model.Token;
import com.lgcns.smartwcs.model.UserRole;
import com.lgcns.smartwcs.repository.TokenMapper;
import io.jsonwebtoken.Claims;
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

    @Value("${wcs.sessionTimeOut}")
    private Long sessionTimeOut;

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

    public String getJWTokenString(Claims claims) throws NoSuchAlgorithmException {
        log.debug("##### getJWTokenString #####");

        byte[] keyBytes = Decoders.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=");
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Random random = SecureRandom.getInstanceStrong();

        String jwtTokenString = Jwts.builder()
                .setIssuer(claims.getIssuer())
                .setSubject("raas")
                .claim("randomInt", random.nextInt())
                .claim("coCd", claims.get("coCd").toString())
                .claim("cntrCd", claims.get("cntrCd").toString())
                .claim("userLvl", claims.get("userLvl").toString())
                .claim("roleCd", claims.get("roleCd") == null ? null : claims.get("roleCd").toString())
                // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
                .setIssuedAt(Date.from(Instant.now()))
                // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
                .setExpiration(Date.from(Instant.now().plusSeconds(sessionTimeOut)))
                .signWith(key,
                        SignatureAlgorithm.HS256
                )
                .compact();

        return jwtTokenString;
    }

    /**
     * 토큰 생성 랩퍼     *
     *
     * @param claims
     * @return
     */
    public Token createToken(Claims claims) throws NoSuchAlgorithmException {
        log.debug("##### createToken ##### " + claims.getIssuer());
        String jwTokenString = getJWTokenString(claims);

        Token tokenData = Token.builder()
                .tokenId(claims.getIssuer())
                .jwTokenString(jwTokenString)
                .useYn("Y")
                .build();

        tokenMapper.createToken(tokenData);

        log.debug("###### Token Created ######" + claims.getIssuer());
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
