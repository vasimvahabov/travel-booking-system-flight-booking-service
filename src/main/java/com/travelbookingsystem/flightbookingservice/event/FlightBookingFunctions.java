package com.travelbookingsystem.flightbookingservice.event;

import com.travelbookingsystem.flightbookingservice.event.messages.FlightBookingPaymentProcessedMessage;
import com.travelbookingsystem.flightbookingservice.service.FlightBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class FlightBookingFunctions {

    @Bean
    public Consumer<Flux<FlightBookingPaymentProcessedMessage>> subscribePaymentProcessedEvent(
            FlightBookingService flightBookingService
    ) {
        return flux -> flightBookingService
                .updateStatus(flux)
                .doOnNext(response -> log.info("Payment processed for Flight Booking ID {}", response.getId()))
                .subscribe();
    }

}
