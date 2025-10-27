package com.travelbookingsystem.flightbookingservice.service.impl;

import com.travelbookingsystem.flightbookingservice.dto.request.FlightBookingRequest;
import com.travelbookingsystem.flightbookingservice.dto.response.FlightBookingResponse;
import com.travelbookingsystem.flightbookingservice.config.flightservice.FlightServiceClient;
import com.travelbookingsystem.flightbookingservice.event.messages.FlightBookingConfirmedMessage;
import com.travelbookingsystem.flightbookingservice.event.messages.FlightBookingPaymentProcessedMessage;
import com.travelbookingsystem.flightbookingservice.mapper.FlightBookingMapper;
import com.travelbookingsystem.flightbookingservice.repository.FlightBookingRepository;
import com.travelbookingsystem.flightbookingservice.service.FlightBookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

import static com.travelbookingsystem.flightbookingservice.entity.constant.FlightBookingStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightBookingServiceImpl implements FlightBookingService {

    FlightBookingRepository flightBookingRepository;
    FlightBookingMapper flightBookingMapper;
    FlightServiceClient flightServiceClient;
    StreamBridge streamBridge;

    @Override
    public Flux<FlightBookingResponse> findAllCreatedBy(UUID user) {
        return flightBookingRepository
                .findAllByCreatedBy(user)
                .map(flightBookingMapper::entityToResponse);
    }

    @Override
    @Transactional
    public Mono<FlightBookingResponse> create(FlightBookingRequest request) {
        return flightServiceClient
                .findByNumber(request.getFlightNumber())
                .map(response -> flightBookingMapper.requestToEntity(request, PENDING))
                .switchIfEmpty(Mono.just(flightBookingMapper.requestToEntity(request, CANCELED)))
                .flatMap(flightBookingRepository::save)
                .map(flightBookingMapper::entityToResponse)
                .doOnNext(this::publishFlightBookingPendingEvent);
    }

    @Override
    public Flux<FlightBookingResponse> updateStatus(Flux<FlightBookingPaymentProcessedMessage> messageFlux) {
        return messageFlux
                .flatMap(message -> flightBookingRepository.findById(message.id()))
                .map(flightBooking -> flightBookingMapper.updateEntity(flightBooking, CONFIRMED))
                .flatMap(flightBookingRepository::save)
                .map(flightBookingMapper::entityToResponse);
    }

    private void publishFlightBookingPendingEvent(FlightBookingResponse flightBookingResponse) {
        if (!PENDING.equals(flightBookingResponse.getStatus())) {
            return;
        }

        var bindingConfirmFlightBooking = "flightBookingPending-out-0";
        var flightBookingConfirmedMessage = new FlightBookingConfirmedMessage(flightBookingResponse.getId());
        streamBridge.send(bindingConfirmFlightBooking, flightBookingConfirmedMessage);

    }

}
