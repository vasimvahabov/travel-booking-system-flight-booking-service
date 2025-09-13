package com.travelbookingsystem.flightbookingservice.config.flightservice;

import com.travelbookingsystem.flightbookingservice.flightservice.dto.response.FlightResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;

@Slf4j
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
                .doOnError(err -> log.error("Error occurred while getting getting flight by number {} : {}", number, err.getMessage()))
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> {
                    log.error("Flight with number {} not found : {}", number, exception.getMessage());
                    return Mono.empty();
                })
                .retryWhen(
                        Retry.backoff(properties.getClient().getBackoffAttempt(),
                                Duration.ofSeconds(properties.getClient().getBackoffMin()))
                )
                .onErrorResume(Exception.class, exception -> {
                    log.error("Exception occurred on getting flight by number {} : {}", number, exception.getMessage());
                    return Mono.empty();
                });
    }

}
