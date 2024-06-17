package com.jino.gateway.fllter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            logRequest(exchange);
            return chain.filter(exchange).then(Mono.fromRunnable(() -> logResponse(exchange)));
        };
    }

    private void logRequest(ServerWebExchange exchange) {
        log.info("Request Method: {}", exchange.getRequest().getMethod());
        log.info("Request URL: {}", exchange.getRequest().getURI());
//        exchange.getRequest().getHeaders().forEach((name, values) ->
//                values.forEach(value -> log.info("Request Header: {}={}", name, value))
//        );
        exchange.getRequest().getQueryParams().forEach((name, values) ->
                values.forEach(value -> log.info("Request Params: {}={}", name, value))
        );
    }

    private void logResponse(ServerWebExchange exchange) {
//        log.info(exchange.getRequest().getURI().toString());
//        log.info(exchange.getRequest().getQueryParams().toString());
//        log.info(exchange.getRequest().getLocalAddress().toString());
//        log.info(exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR).toString());
        exchange.getResponse().getHeaders().forEach((name, values) ->
                values.forEach(value -> log.info("Response Header: {}={}", name, value))
        );
        log.info(exchange.getResponse().getStatusCode().toString());
        log.info("Response : " + exchange.mutate().response(exchange.getResponse()).build());

    }

    public static class Config {
        // Put configuration properties here if needed
    }
}