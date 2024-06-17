package com.jino.gateway.fllter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
public class AddVSLOpenApiFilter extends AbstractGatewayFilterFactory<AddVSLOpenApiFilter.Config> {

    @Value("${vslOpenApi.api-key}")
    private String apiKey;

    public AddVSLOpenApiFilter() {
        super(AddVSLOpenApiFilter.Config.class);
    }
    @Override
    public GatewayFilter apply(AddVSLOpenApiFilter.Config config) {
        return (exchange, chain) -> {

            URI uri = exchange.getRequest().getURI();
            URI newUri = UriComponentsBuilder.fromUri(uri)
                    .queryParam("apiKey", apiKey)
                    .queryParam("getType", "json")
                    .build(true)
                    .toUri();

            ServerHttpRequest request = exchange.getRequest().mutate()
                    .uri(newUri)
                    .build();

            ServerWebExchange modifiedExchange = exchange.mutate().request(request).build();

            return chain.filter(modifiedExchange);
        };
    }

    public static class Config {
        // Put configuration properties here if needed
    }

}
