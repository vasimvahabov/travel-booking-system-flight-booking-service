package com.travelbookingsystem.flightbookingservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightBookingRequest {

    Long id;

    @NotNull(message = "The user id cannot be null!")
    Long userId;

    @NotNull(message = "The flight number cannot be null!")
    String flightNumber;

}
