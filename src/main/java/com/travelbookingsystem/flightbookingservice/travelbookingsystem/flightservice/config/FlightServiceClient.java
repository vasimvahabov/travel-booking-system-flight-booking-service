package com.travelbookingsystem.flightbookingservice.travelbookingsystem.flightservice.config;

import com.travelbookingsystem.flightbookingservice.travelbookingsystem.flightservice.dto.response.FlightResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightServiceClient {

    WebClient webClient;
    FlightServiceConfigProperties properties;

    public Mono<FlightResponse> findByNumber(String number) {
        return webClient
                .get()
                .uri(String.format("%s/%s", properties.getRootApi(), number))
                .retrieve()
                .bodyToMono(FlightResponse.class)
                .timeout(Duration.ofSeconds(properties.getClient().getTimeout()), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty())
                .retryWhen(
                        Retry.backoff(properties.getClient().getBackoffAttempt(),
                                Duration.ofSeconds(properties.getClient().getBackoffMin()))
                )
                .onErrorResume(Exception.class, exception -> Mono.empty());
    }

}
