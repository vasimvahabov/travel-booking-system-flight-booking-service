package com.travelbookingsystem.flightbookingservice.entity;

import com.travelbookingsystem.flightbookingservice.entity.constant.FlightBookingStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("flight_bookings")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightBooking {

    @Id
    Long id;

    @Version
    @Builder.Default
    Integer version = 0;

    Long flightId;

    Long userId;

    FlightBookingStatus status;

    @CreatedDate
    @Builder.Default
    Instant createdDateTime = Instant.now();

    @LastModifiedDate
    Instant lastModifiedDateTime = Instant.now();

}