package com.travelbookingsystem.flightbookingservice.flightservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FlightServiceClientConfig {

    @Bean
    WebClient webClient(FlightServiceClientConfigProperties properties,
                        WebClient.Builder builder) {
        return builder.baseUrl(properties.getUri()).build();
    }

}
