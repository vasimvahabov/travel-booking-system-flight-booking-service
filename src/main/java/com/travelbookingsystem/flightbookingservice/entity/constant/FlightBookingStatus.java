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

    PENDING(1, "Pending"),
    CONFIRMED(2, "Confirmed"),
    CANCELED(3, "Cancelled");

    int value;
    String description;

    public static FlightBookingStatus fromValue(int value) {
        return Arrays.stream(FlightBookingStatus.values())
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("FlightBookingStatus doesn't exist with value %s".formatted(value)));
    }

}
