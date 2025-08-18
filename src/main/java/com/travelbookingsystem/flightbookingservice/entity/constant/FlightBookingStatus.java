package com.travelbookingsystem.flightbookingservice.entity.constant;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum FlightBookingStatus {

    PENDING(1, "Pending"),
    CONFIRMED(2, "Confirmed"),
    CANCELED(3, "Cancelled");

    int value;
    String description;

}
