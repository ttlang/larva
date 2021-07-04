package com.larva.api.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class BasePatchFilter  extends AbstractGatewayFilterFactory<Object> {

    @Value("${spring.webflux.base-path}")
    private String basePath;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            var req = exchange.getRequest();
            var path = req.getURI().getRawPath();
            var newPath = path.replaceFirst(basePath, "");
            var request = req.mutate().path(newPath).contextPath("").build();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

}
