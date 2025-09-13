package com.travelbookingsystem.flightbookingservice.service;

import com.travelbookingsystem.flightbookingservice.dto.request.FlightBookingRequest;
import com.travelbookingsystem.flightbookingservice.dto.response.FlightBookingResponse;
import com.travelbookingsystem.flightbookingservice.event.messages.FlightBookingPaymentProcessedMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlightBookingService {

    Flux<FlightBookingResponse> findAll();

    Mono<FlightBookingResponse> create(FlightBookingRequest request);

    Flux<FlightBookingResponse> updateStatus(Flux<FlightBookingPaymentProcessedMessage> messageFlux);

}
