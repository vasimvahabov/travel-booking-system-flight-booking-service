package com.travelbookingsystem.flightbookingservice.controller;

import com.travelbookingsystem.flightbookingservice.dto.request.FlightBookingRequest;
import com.travelbookingsystem.flightbookingservice.dto.response.FlightBookingResponse;
import com.travelbookingsystem.flightbookingservice.service.FlightBookingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flight-bookings")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightBookingController {

    FlightBookingService flightBookingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<FlightBookingResponse> findAll(@AuthenticationPrincipal Jwt jwt) {
        return flightBookingService.findAllCreatedBy(UUID.fromString(jwt.getSubject()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FlightBookingResponse> create(@RequestBody @Valid FlightBookingRequest request) {
        return flightBookingService.create(request);
    }

}
