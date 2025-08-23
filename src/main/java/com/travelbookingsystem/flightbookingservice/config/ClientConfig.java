package com.travelbookingsystem.flightbookingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    WebClient webClient(ClientConfigProperties properties,
                        WebClient.Builder builder) {
        return builder.baseUrl(properties.getFlightServiceUri()).build();
    }

}
