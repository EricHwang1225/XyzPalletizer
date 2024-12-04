package com.lgcns.smartwcs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;


//@Configuration
//@EnableWebFlux
public class CORSConfiguration implements WebFluxConfigurer {
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
}
