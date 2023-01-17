package com.example.apigateway.filter;

import com.kastourik12.clients.clients.ClientDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientFilter extends AbstractGatewayFilterFactory<ClientFilter.Config> {
    private final Logger logger ;
    private final WebClient.Builder webClientBuilder;
    @Value("${client.instancesUri.client}")
    private String clientServiceUrl;
    private final ModifyRequestBodyGatewayFilterFactory factory;


    @Autowired
    public ClientFilter(WebClient.Builder webClientBuilder,ModifyRequestBodyGatewayFilterFactory factory) {
        super(ClientFilter.Config.class);
        this.webClientBuilder = webClientBuilder;
        this.logger = LoggerFactory.getLogger(AuthFilter.class);
        this.factory = factory;
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing authorization information");
            }
            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new RuntimeException("Incorrect authorization structure");
            }
            String token = parts[1];
            logger.info("inside auth filter : validating access token");
            return webClientBuilder.build()
                    .get()
                    .uri(clientServiceUrl+"?token=" + token)
                    .retrieve().bodyToMono(ClientDTO.class)
                    .map(clientDTO -> {
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("x-auth-client-id", String.valueOf(clientDTO.getClientId()));
                        headers.add("x-auth-user-id", String.valueOf(clientDTO.getUserId()));
                        exchange.getRequest().mutate()
                                .header("x-auth-client-id", String.valueOf(clientDTO.getClientId()))
                                .header("x-auth-user-id", String.valueOf(clientDTO.getUserId()));
                        return exchange;
                    }).flatMap(chain::filter);

        };
    }
    public static class Config {
        // Put the configuration properties for your filter here
    }
}
