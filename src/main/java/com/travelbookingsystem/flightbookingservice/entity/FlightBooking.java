package com.travelbookingsystem.flightbookingservice.entity;

import com.travelbookingsystem.flightbookingservice.entity.constant.FlightBookingStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Sequence;
import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("flight_bookings")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightBooking {

    @Id
    @Sequence(sequence = "seq_flight_booking_id")
    Long id;

    @Version
    Integer version;

    Long userId;

    String flightNumber;

    FlightBookingStatus status;

    @CreatedDate
    Instant createdDateTime;

    @LastModifiedDate
    Instant lastModifiedDateTime;

    @CreatedBy
    UUID createdBy;

    @LastModifiedBy
    UUID lastModifiedBy;

}
