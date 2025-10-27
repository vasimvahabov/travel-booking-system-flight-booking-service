package com.travelbookingsystem.flightbookingservice.repository;

import com.travelbookingsystem.flightbookingservice.entity.FlightBooking;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Repository
public interface FlightBookingRepository extends ReactiveCrudRepository<FlightBooking, Long> {

    Flux<FlightBooking> findAllByCreatedBy(UUID user);

}
