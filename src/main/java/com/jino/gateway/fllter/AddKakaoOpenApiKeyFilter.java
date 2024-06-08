package com.jino.gateway.fllter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AddKakaoOpenApiKeyFilter extends AbstractGatewayFilterFactory<AddKakaoOpenApiKeyFilter.Config> {

    @Value("${kakao.api-key}")
    private String apiKey;

    public AddKakaoOpenApiKeyFilter() {
        super(AddKakaoOpenApiKeyFilter.Config.class);
    }

    @Override
    public GatewayFilter apply(AddKakaoOpenApiKeyFilter.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("kakao-open-key", apiKey)
                    .build();
            ServerWebExchange modifiedExchange = exchange.mutate().request(request).build();
            return chain.filter(modifiedExchange);
        };
    }

    public static class Config {
        // Put configuration properties here if needed
    }
}
