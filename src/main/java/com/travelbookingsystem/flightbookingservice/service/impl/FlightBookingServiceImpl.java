package com.travelbookingsystem.flightbookingservice.service.impl;

import com.travelbookingsystem.flightbookingservice.dto.request.FlightBookingRequest;
import com.travelbookingsystem.flightbookingservice.dto.response.FlightBookingResponse;
import com.travelbookingsystem.flightbookingservice.entity.constant.FlightBookingStatus;
import com.travelbookingsystem.flightbookingservice.travelbookingsystem.flightservice.config.FlightServiceClient;
import com.travelbookingsystem.flightbookingservice.mapper.FlightBookingMapper;
import com.travelbookingsystem.flightbookingservice.repository.FlightBookingRepository;
import com.travelbookingsystem.flightbookingservice.service.FlightBookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightBookingServiceImpl implements FlightBookingService {

    FlightBookingRepository flightBookingRepository;
    FlightBookingMapper flightBookingMapper;
    FlightServiceClient flightServiceClient;

    @Override
    public Flux<FlightBookingResponse> findAll() {
        return flightBookingRepository.findAll().map(flightBookingMapper::entityToResponse);
    }

    @Override
    public Mono<FlightBookingResponse> create(FlightBookingRequest request) {
        return flightServiceClient
                .findByNumber(request.getFlightNumber())
                .map(response ->
                     flightBookingMapper.requestToEntity(request, FlightBookingStatus.CONFIRMED)
                )
                .switchIfEmpty(Mono.defer(
                        () -> Mono.just(flightBookingMapper.requestToEntity(request, FlightBookingStatus.CANCELED))
                ))
                .flatMap(flightBookingRepository::save)
                .map(flightBookingMapper::entityToResponse);
    }

}