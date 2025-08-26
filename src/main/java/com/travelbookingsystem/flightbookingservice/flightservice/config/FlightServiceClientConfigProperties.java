package com.travelbookingsystem.flightbookingservice.flightservice.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "travel-booking-system.flight-service")
public class FlightServiceClientConfigProperties {

    Long timeout;

    Long backoffAttempt;

    Long backoffMin;

    String uri;

}
