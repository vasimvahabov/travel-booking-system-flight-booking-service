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
public class FlightServiceConfigProperties {

    String uri;

    String rootApi;

    Client client = new Client();

    @Getter
    @Setter
    static class Client {

        Long timeout;

        Long backoffAttempt;

        Long backoffMin;

    }

}
