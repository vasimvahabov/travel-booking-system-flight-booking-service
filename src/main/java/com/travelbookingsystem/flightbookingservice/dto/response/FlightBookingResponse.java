package com.travelbookingsystem.flightbookingservice.dto.response;

import com.travelbookingsystem.flightbookingservice.entity.constant.FlightBookingStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightBookingResponse {

    Long id;

    Long flightId;

    Long userId;

    FlightBookingStatus status;

}
