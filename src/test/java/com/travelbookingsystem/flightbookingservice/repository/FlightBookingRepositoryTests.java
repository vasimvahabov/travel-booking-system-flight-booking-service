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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

@DataR2dbcTest(properties = {
        "logging.level.io.r2dbc.postgresql=DEBUG"
})
@Testcontainers
@Import({AuditConfig.class, FlightBookingStatusConverter.class, FlightBookingSequenceCallback.class})
@FieldDefaults(level = AccessLevel.PRIVATE)
class FlightBookingRepositoryTests {

    final String USER_ID = "182a5a6e-918b-4110-ba0c-8300f6e90662";

    @Container
    static PostgreSQLContainer<?> postgresContainer
            = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.5"));

    @Autowired
    FlightBookingRepository flightBookingRepository;

    @DynamicPropertySource
    private static void postgresProperties(DynamicPropertyRegistry registry) {
        var r2dbcUrl = "r2dbc:postgresql://%s:%s/%s".formatted(
                postgresContainer.getHost(),
                postgresContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                postgresContainer.getDatabaseName()
        );

        registry.add("spring.r2dbc.url", () -> r2dbcUrl);
        registry.add("spring.r2dbc.username", postgresContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresContainer::getPassword);
        registry.add("spring.liquibase.url", postgresContainer::getJdbcUrl);
    }

    @Test
    @WithMockUser(USER_ID)
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
