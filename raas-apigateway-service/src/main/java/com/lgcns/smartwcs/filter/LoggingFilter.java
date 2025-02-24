package com.lgcns.smartwcs.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        //return ((exchange, chain) -> {
        //    ServerHttpRequest request = exchange.getRequest();
        //    ServerHttpResponse response = exchange.getResponse();

        //    log.info("Global Fileter baseMessage: {}", config.getBaseMessage());

        //   if (config.isPreLogger()){
        //        log.info("Global Fileter Start: response code -> {}", request.getId());
        //    }

        //    return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        //        if (config.isPostLogger()) {
        //            log.info("Global Fileter End: response code -> {}", response.getStatusCode());
        //        }
        //    }));
        //});
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Fileter baseMessage: {}", config.getBaseMessage());

           if (config.isPreLogger()){
                log.info("Logging PRE Fileter: request id -> {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Logging POST Fileter: response code -> {}", response.getStatusCode());
                }
            }));
        }, Ordered.LOWEST_PRECEDENCE); //Highest로 잡으면 Global 보다 먼저 실행됨

        return filter;
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
