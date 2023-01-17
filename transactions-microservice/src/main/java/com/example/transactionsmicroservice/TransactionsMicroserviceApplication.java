package com.example.transactionsmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(
        scanBasePackages = {
                "com.example.transactionsmicroservice",
                "com.kastourik12.amqp",
        }
)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.kastourik12.clients")
public class TransactionsMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsMicroserviceApplication.class, args);
    }

}
