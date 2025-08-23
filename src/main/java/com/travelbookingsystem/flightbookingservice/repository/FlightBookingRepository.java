package com.travelbookingsystem.flightbookingservice.repository;

import com.travelbookingsystem.flightbookingservice.entity.FlightBooking;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightBookingRepository extends ReactiveCrudRepository<FlightBooking, Long> {

}
