package com.travelbookingsystem.flightbookingservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FlightBookingRequest that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
