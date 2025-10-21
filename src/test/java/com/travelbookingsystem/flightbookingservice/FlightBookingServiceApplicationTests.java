package com.travelbookingsystem.flightbookingservice;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@FieldDefaults(level = AccessLevel.PRIVATE)
class FlightBookingServiceApplicationTests {

    @Container
    static final PostgreSQLContainer<?> POSTGRES_CONTAINER
            = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.5"));

    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        var r2dbcUrl = "r2dbc:postgresql://%s:%s%s".formatted(
                POSTGRES_CONTAINER.getHost(),
                POSTGRES_CONTAINER.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                POSTGRES_CONTAINER.getDatabaseName()
        );

        registry.add("spring.r2dbc.url", () -> r2dbcUrl);
        registry.add("spring.r2dbc.username", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.r2dbc.password", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.liquibase.url", POSTGRES_CONTAINER::getJdbcUrl);
    }

    @Test
    void contextLoads() {
    }

}