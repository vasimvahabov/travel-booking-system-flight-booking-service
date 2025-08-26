package com.travelbookingsystem.flightbookingservice.flightservice.config;

import com.travelbookingsystem.flightbookingservice.flightservice.dto.response.FlightResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;

import static com.travelbookingsystem.flightbookingservice.constant.ApplicationConstants.*;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightServiceClient {

    WebClient webClient;
    FlightServiceClientConfigProperties properties;

    public Mono<FlightResponse> findByNumber(String number) {
        return webClient
                .get()
                .uri(String.format("%s/%s", FLIGHT_SERVICE_ROOT_API, number))
                .retrieve()
                .bodyToMono(FlightResponse.class)
                .timeout(Duration.ofSeconds(properties.getTimeout()), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty())
                .retryWhen(
                        Retry.backoff(properties.getBackoffAttempt(),
                                Duration.ofSeconds(properties.getBackoffMin()))
                )
                .onErrorResume(Exception.class, exception -> Mono.empty());
    }

}
