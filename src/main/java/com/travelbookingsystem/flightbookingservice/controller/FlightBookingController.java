package com.travelbookingsystem.flightbookingservice.controller;

import com.travelbookingsystem.flightbookingservice.dto.request.FlightBookingRequest;
import com.travelbookingsystem.flightbookingservice.dto.response.FlightBookingResponse;
import com.travelbookingsystem.flightbookingservice.service.FlightBookingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flight-bookings")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightBookingController {

    FlightBookingService flightBookingService;

    @GetMapping
    public ResponseEntity<Flux<FlightBookingResponse>> findAll() {
        return ResponseEntity.ok(flightBookingService.findAll());
    }

    @PostMapping
    public ResponseEntity<Mono<FlightBookingResponse>> create(@RequestBody @Valid FlightBookingRequest request) {
        return new ResponseEntity<>(flightBookingService.create(request), HttpStatus.CREATED);
    }

}
