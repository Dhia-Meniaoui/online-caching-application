package com.example.apigateway.filter.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class PreFilter implements GlobalFilter {
    private final Logger logger = LoggerFactory.getLogger(PreFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Pre Filter executed");
        return chain.filter(exchange).then(
                Mono.fromRunnable(()-> {
            logger.info("Global Post-filter executed...");
            exchange.getRequest().mutate().header("Access-Control-Allow-Origin", "*");
        }));
    }

}
