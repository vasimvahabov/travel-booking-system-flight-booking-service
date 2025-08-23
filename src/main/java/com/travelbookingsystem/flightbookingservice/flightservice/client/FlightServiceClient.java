package com.travelbookingsystem.flightbookingservice.flightservice.client;

import com.travelbookingsystem.flightbookingservice.flightservice.dto.response.FlightResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.travelbookingsystem.flightbookingservice.constant.ApplicationConstants.*;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightServiceClient {

    WebClient webClient;

    public Mono<FlightResponse> findByNumber(String number) {
        return webClient
                .get()
                .uri(String.format("%s/%s", FLIGHT_SERVICE_ROOT_API, number))
                .retrieve()
                .bodyToMono(FlightResponse.class);
    }

}
