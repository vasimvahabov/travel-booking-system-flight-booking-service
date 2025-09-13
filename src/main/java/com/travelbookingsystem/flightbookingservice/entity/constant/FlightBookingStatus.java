package com.travelbookingsystem.flightbookingservice.entity.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum FlightBookingStatus {

    CANCELED(1, "Cancelled"),
    PENDING(2, "Pending"),
    CONFIRMED(3, "Confirmed");

    int value;
    String description;

    public static FlightBookingStatus fromValue(int value) {
        return Arrays.stream(FlightBookingStatus.values())
                .filter(
                        status -> status.getValue() == value
                ).findFirst()
                .orElseThrow(() ->
                        new RuntimeException("FlightBookingStatus doesn't exist with value %s".formatted(value)));
    }

}
