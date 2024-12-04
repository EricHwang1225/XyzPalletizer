/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.config;

import com.lgcns.smartwcs.login.intercepter.UserInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <PRE>
 * WebMvc를 위한 설정.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        // 언어&국가정보가 없는 경우 미국으로 인식하도록 설정
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/i18n/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() throws IOException {
        return new MessageSourceAccessor(messageSource());
    }

    /**
     * CORS 맵핑 추가.
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 전체 경로에 CORS설정
        registry.addMapping("/**")
                // 모든 주소에서 접근 가능
                //.allowedOrigins("https://raas.singlex.com")
                .allowedOriginPatterns("https://raas.singlex.com", "https://raas-dev.singlex.com", "https://raas-stg.singlex.com", "https://raas-dr.singlex.com", "http://localhost:5173")
                // 모든 메소드 가능
                .allowedMethods("*")
                // response의 jwtoken을 클라이언트에서 접근할 수 있도록 허용
                .exposedHeaders("jwtoken", "id");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePaths = new ArrayList<>();
        excludePaths.add("/v2/api-docs");
        excludePaths.add("/swagger-resources/**");
        excludePaths.add("/swagger-ui.html");
        excludePaths.add("/webjars/**");
        excludePaths.add("/interface/**");
        excludePaths.add("/api/common/login");
        excludePaths.add("/api/common/login/**");
        excludePaths.add("/api/as/**");
        excludePaths.add("/backup/**");
        excludePaths.add("/data-check/**");
        excludePaths.add("/api/healthCheck");
        excludePaths.add("/**");

        registry.addInterceptor(userInterceptor())
                .excludePathPatterns(excludePaths);
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }
}