package com.travelbookingsystem.flightbookingservice.repository;

import com.travelbookingsystem.flightbookingservice.config.AuditConfig;
import com.travelbookingsystem.flightbookingservice.entity.FlightBooking;
import com.travelbookingsystem.flightbookingservice.entity.callback.FlightBookingSequenceCallback;
import com.travelbookingsystem.flightbookingservice.entity.constant.FlightBookingStatus;
import com.travelbookingsystem.flightbookingservice.entity.converter.FlightBookingStatusConverter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Testcontainers
@Import({AuditConfig.class, FlightBookingStatusConverter.class, FlightBookingSequenceCallback.class})
@FieldDefaults(level = AccessLevel.PRIVATE)
class FlightBookingRepositoryTests {

    @Container
    static PostgreSQLContainer<?> postgresContainer
            = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.5"));

    @Autowired
    FlightBookingRepository flightBookingRepository;

    @DynamicPropertySource
    private static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", FlightBookingRepositoryTests::r2dbcUrl);
        registry.add("spring.r2dbc.username", postgresContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresContainer::getPassword);
        registry.add("spring.liquibase.url", postgresContainer::getJdbcUrl);
    }

    private static String r2dbcUrl() {
        return "r2dbc:postgresql://%s:%s/%s".formatted(
                postgresContainer.getHost(),
                postgresContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                postgresContainer.getDatabaseName()
        );
    }

    @Test
    void createCancelledFlightBooking() {
        var cancelled = FlightBooking.builder()
                .flightNumber("CC333")
                .status(FlightBookingStatus.CANCELED)
                .userId(9888L).build();
        StepVerifier.create(flightBookingRepository.save(cancelled))
                .expectNextMatches(flightBooking ->
                        FlightBookingStatus.CANCELED.equals(flightBooking.getStatus())
                ).verifyComplete();
    }

}
