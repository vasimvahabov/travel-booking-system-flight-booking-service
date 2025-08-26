package com.travelbookingsystem.flightbookingservice.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.travelbookingsystem.flightbookingservice.config.flightservice.FlightServiceConfigProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "travel-booking-system")
public class TravelBookingSystemConfigProperties {

    @JsonProperty("flight-service")
    FlightServiceConfigProperties flightService;

}
