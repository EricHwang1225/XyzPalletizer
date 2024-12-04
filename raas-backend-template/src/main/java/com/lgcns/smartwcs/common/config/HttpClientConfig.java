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

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * HttpClient를 위한 설정.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Configuration
public class HttpClientConfig {

    @Value("${httpclient.setting.connection-timeout}")
    private Integer connTimeout;

    @Value("${httpclient.setting.read-timeout}")
    private Integer readTimeout;

    @Value("${httpclient.setting.retry-policy}")
    private Integer retryPolicy;

    @Value("${httpclient.setting.max-conn-per-route}")
    private Integer maxConnPerRoute;

    @Value("${httpclient.setting.max-conn-total}")
    private Integer maxConnTotal;

    @Bean
    public RestTemplate restTemplate() {
        HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnPerRoute(maxConnPerRoute)
                .setMaxConnTotal(maxConnTotal)
                .build();

        org.apache.hc.client5.http.classic.HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(cm)
                .build();

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(connTimeout);
        httpRequestFactory.setConnectionRequestTimeout(readTimeout);
        httpRequestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(httpRequestFactory));

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(clientHttpRequestInterceptor());
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

    public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
        return (request, body, execution) -> {
            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(retryPolicy));
            try {
                return retryTemplate.execute(context -> execution.execute(request, body));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean(name = "sslIgnoreRestTemplate")
    public RestTemplate sslIgnoreRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(
                SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
                        .build(), NoopHostnameVerifier.INSTANCE);

        HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnPerRoute(maxConnPerRoute)
                .setMaxConnTotal(maxConnTotal)
                .setSSLSocketFactory(csf)
                .build();
        HttpClient httpClient = HttpClients.custom().setConnectionManager(cm).evictExpiredConnections().build();

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(connTimeout);
        httpRequestFactory.setConnectionRequestTimeout(readTimeout);
        httpRequestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(httpRequestFactory));

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(clientHttpRequestInterceptor());
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}
