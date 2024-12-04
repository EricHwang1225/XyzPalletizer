/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.login.intercepter;

import com.lgcns.smartwcs.login.model.Token;
import com.lgcns.smartwcs.login.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;

/**
 * <PRE>
 * 사용자 인터셉터 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    TokenService tokenService;

    public boolean refreshWhenValid(Claims claims, String jsonWebToken, String requestMethod, String requestUri) {

        // 1. Black Token인지 확인 함
        log.debug("##### Check BlackToken ##### " + claims.getIssuer());
        boolean isBlacktoken = tokenService.isBlackToken(claims.getIssuer(), jsonWebToken);
        if (isBlacktoken) {
            log.debug("##### Black Token detected ##### " + claims.getIssuer());
            return false;
        }
        // 요청 토큰이 정상적인지 확인


        try {
            // 2. Black Token이 아니면 날짜가 현시간 보다 작으면 return false
            Date exp = claims.getExpiration();

            // Token에서 만료 Exception이 발생하나 혹시 몰라서 if로 한번 더 처리함.
            Date now = Date.from(Instant.now());
            if (exp.before(now)) {
                tokenService.updateBlackToken(claims.getIssuer(), jsonWebToken);
                return false;
            }

            // 3. Role Check
            if (!checkRole(claims, requestMethod, requestUri)) {
                return false;
            }

        } catch (MalformedJwtException e) {
            return false;
        }

        return true;
    }

    private boolean checkRole(Claims claims, String requestMethod, String requestUri) {

        String userLvl = claims.get("userLvl").toString();
        String coCd = claims.get("coCd").toString();

        if (userLvl.equals("1")) {
            // 3-1. userLvl == 1 이면 무조건 Pass
            return true;
        } else if (userLvl.equals("2")) {
            // 3-2. userLvl == 2 이면 coCd에 methodUri 있으면 OK
            return tokenService.isAccessibleMenuByTenant(requestUri, coCd);
        } else {
            // 3-3. userLvl == 3 이면 coCd, cntrCd, roleCd 로 메뉴 가져와서 있으면 OK
            String cntrCd = claims.get("cntrCd").toString();
            String roleCd = claims.get("roleCd").toString();
            return tokenService.isAccessibleMenuByCenter(requestMethod, requestUri, coCd, cntrCd, roleCd);
        }

    }

    /**
     * 핸들러 동작전 처리 true/false 에 따라 다음 과정 처리를 수행
     *
     * @param request  요청
     * @param response 응답
     * @param handler  처리 핸들러
     * @return returnValue 정합성 체크 결과
     * @throws Exception 발생한 예외
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandle ------------------------------------------------------------");
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String requestMethod = request.getMethod();
        String requestUri = request.getRequestURI();

        // 토큰은 헤더에만 보관
        String jsonWebToken = request.getHeader("jwtoken");
        log.debug("##### request of jsonWebToken ##### " + request.getRequestURI() + " " + jsonWebToken);

        if (!StringUtils.hasText(jsonWebToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Decoders.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="))
                    .build().parseClaimsJws(jsonWebToken).getBody();

            if (refreshWhenValid(claims, jsonWebToken, requestMethod, requestUri)) {

                Token tokenData = tokenService.createToken(claims.getIssuer()); //다시 생성하여 새로운 토큰 추가.
                log.debug("### 다시 생성된 tokenData ###\n" + tokenData.toString());

                response.setHeader("jwtoken", tokenData.getJwTokenString());
                return true;
            }

        } catch (NullPointerException npe) {
            log.error(npe.getMessage(), npe);
        } catch (ExpiredJwtException jwtException) {
            log.error(jwtException.getMessage(), jwtException);
        }

        log.debug("No token. jwt:" + jsonWebToken + ")");

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}