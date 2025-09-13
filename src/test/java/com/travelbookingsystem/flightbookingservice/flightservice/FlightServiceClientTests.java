package com.travelbookingsystem.flightbookingservice.flightservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.travelbookingsystem.flightbookingservice.config.TravelBookingSystemConfigProperties;
import com.travelbookingsystem.flightbookingservice.config.flightservice.FlightServiceClient;
import com.travelbookingsystem.flightbookingservice.config.flightservice.FlightServiceConfigProperties;
import com.travelbookingsystem.flightbookingservice.flightservice.dto.response.FlightResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.shaded.org.yaml.snakeyaml.Yaml;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.io.IOException;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@TestMethodOrder(MethodOrderer.Random.class)
class FlightServiceClientTests {

    MockWebServer mockWebServer;
    FlightServiceClient flightServiceClient;

    @BeforeEach
    void setUpBeforeEach() throws IOException {

        FlightServiceConfigProperties configProperties;
        try (var inputStream = getClass().getClassLoader().getResourceAsStream("application-test.yml")) {
            Map<String, Map<String, Object>> loaded = new Yaml().load(inputStream);
            var travelBookingSystemConfigProps = new ObjectMapper()
                    .setPropertyNamingStrategy(PropertyNamingStrategies.KEBAB_CASE)
                    .convertValue(loaded.get("travel-booking-system"),
                            TravelBookingSystemConfigProperties.class);
            configProperties = travelBookingSystemConfigProps.getFlightService();
        }

        mockWebServer = new MockWebServer();
        mockWebServer.start();
        var webClient = WebClient
                .builder()
                .baseUrl(mockWebServer.url("/").uri().toString())
                .build();
        flightServiceClient = new FlightServiceClient(webClient, configProperties);
    }

    @AfterEach
    void clean() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void whenFlightExitsReturnFlight() {
        var flightNumber = "AA144";
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody("""
                        {
                            "airplaneId": 1,
                            "number": "%s",
                            "departureAirportCode": "DUB",
                            "arrivalAirportCode": "FRA",
                            "departureDateTime": "2025-07-15 00:12",
                            "arrivalDateTime": "2025-07-15 05:00",
                            "price": 15.99
                        }""".formatted(flightNumber));
        mockWebServer.enqueue(mockResponse);

        Mono<FlightResponse> response = flightServiceClient.findByNumber(flightNumber);
        StepVerifier.create(response)
                .expectNextMatches(flight -> flightNumber.equals(flight.getNumber()))
                .verifyComplete();
    }

    @Test
    void whenFlightNotExistsReturnEmpty() {
        var flightNumber = "AA566";
        var mockResponse = new MockResponse()
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setResponseCode(HttpStatus.NO_CONTENT.value());
        mockWebServer.enqueue(mockResponse);

        Mono<FlightResponse> response = flightServiceClient.findByNumber(flightNumber);
        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();
    }
}
