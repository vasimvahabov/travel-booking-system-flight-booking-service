package com.travelbookingsystem.flightbookingservice.travelbookingsystem.flightservice.config;

import com.travelbookingsystem.flightbookingservice.travelbookingsystem.config.TravelBookingSystemConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FlightServiceClientConfig {

    @Bean
    WebClient webClient(TravelBookingSystemConfigProperties properties,
                        WebClient.Builder builder) {
        return builder.baseUrl(
                properties.getFlightService().getUri()
        ).build();
    }

}
