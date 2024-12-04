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

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <PRE>
 * Swagger 설정을 위한 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private static final String API_NAME = "SmartWCS API";
    private static final String API_VERSION = "0.1.0";
    private static final String API_DESCRIPTION = "SmartWCS API Document";

    /**
     * Swagger 정보 설정 객체 생성용 메소드.
     * @return
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("raas-common")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Raas Common API")
                        .description("Raas Common API application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

    @Getter
    @Setter
    @Schema
    static class Pages {
        @Schema(description = "페이지 번호(0..N)")
        private Integer page;

        @Schema(description = "페이지 크기", allowableValues="range[0, 200]")
        private Integer size;

        @Schema(description = "정렬(사용법: 컬럼명,ASC|DESC)")
        private List<String> sort;
    }
}
