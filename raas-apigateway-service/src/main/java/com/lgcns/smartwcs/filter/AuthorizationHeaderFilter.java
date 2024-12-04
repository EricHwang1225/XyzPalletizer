package com.lgcns.smartwcs.filter;

import com.lgcns.smartwcs.model.Token;
import com.lgcns.smartwcs.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;

    @Autowired
    TokenService tokenService;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config{

    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            log.debug("Start Check JWT ------------------------------------------------------------");
            if ("OPTIONS".equals(request.getMethod())) {
                return chain.filter(exchange);
            }

            String requestMethod = String.valueOf(request.getMethod());
            String requestUri = String.valueOf(request.getPath());

            String jsonWebToken;
            // 토큰은 헤더에만 보관
            try {
                jsonWebToken = request.getHeaders().get("jwtoken").get(0);
            } catch(Exception e){
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            log.debug("##### request of jsonWebToken ##### " + requestUri + " " + jsonWebToken);

            if (!StringUtils.hasText(jsonWebToken)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Decoders.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="))
                        .build().parseClaimsJws(jsonWebToken).getBody();

                if (refreshWhenValid(claims, jsonWebToken, requestMethod, requestUri)) {

                    return createToken(exchange, chain, claims);
                }

            } catch (NullPointerException npe) {
                log.error(npe.getMessage(), npe);
            } catch (ExpiredJwtException jwtException) {
                log.error(jwtException.getMessage(), jwtException);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }


            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if (!isJwtValid(jwt)){
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

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


    public Mono<Void> createToken(ServerWebExchange exchange, GatewayFilterChain chain, Claims claims) throws NoSuchAlgorithmException {

        Token tokenData = tokenService.createToken(claims); //다시 생성하여 새로운 토큰 추가.

        log.debug("### 다시 생성된 tokenData ###\n" + tokenData.toString());

        ServerHttpResponse response = exchange.getResponse();
        response.beforeCommit(() -> {
            response.getHeaders().remove("jwtoken");
            response.getHeaders().set("jwtoken", tokenData.getJwTokenString());
            return Mono.empty();
        });

        return chain.filter(exchange);

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

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        String subject = null;

        try {
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt).getBody()
                    .getSubject();
        } catch (Exception ex) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }
        return returnValue;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();

    }



}
