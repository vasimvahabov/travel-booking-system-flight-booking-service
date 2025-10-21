package com.travelbookingsystem.flightbookingservice.controller;

import com.travelbookingsystem.flightbookingservice.config.SecurityConfig;
import com.travelbookingsystem.flightbookingservice.dto.request.FlightBookingRequest;
import com.travelbookingsystem.flightbookingservice.dto.response.FlightBookingResponse;
import com.travelbookingsystem.flightbookingservice.entity.constant.FlightBookingStatus;
import com.travelbookingsystem.flightbookingservice.service.FlightBookingService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.travelbookingsystem.flightbookingservice.config.ApplicationConstants.*;

@WebFluxTest(FlightBookingController.class)
@Import(SecurityConfig.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightBookingControllerTests {

    @Autowired
    WebTestClient webTestClient;

    @MockitoBean
    FlightBookingService flightBookingService;

    @MockitoBean
    ReactiveJwtDecoder reactiveJwtDecoder;

    @Test
    void givenRoleCustomer_whenFlightNotAvailable_thenRejectFlightBooking() {
        var flightNumber = "CC333";
        var id = 9888L;
        var userId = 8999L;
        var status = FlightBookingStatus.CANCELED;

        var request = FlightBookingRequest
                .builder()
                .flightNumber(flightNumber)
                .userId(userId).build();

        var flightBookingResponse = FlightBookingResponse.builder()
                .id(id)
                .flightNumber(flightNumber)
                .status(status)
                .userId(id).build();
        BDDMockito.given(flightBookingService.create(request))
                .willReturn(Mono.just(flightBookingResponse));

        webTestClient.mutateWith(
                        SecurityMockServerConfigurers
                                .mockJwt()
                                .authorities(new SimpleGrantedAuthority(AUTHORITY_CUSTOMER))
                ).post()
                .uri("/api/v1/flight-bookings")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightBookingResponse.class)
                .value(response -> {
                    Assertions.assertThat(response).isNotNull();
                    Assertions.assertThat(flightBookingResponse.getId()).isEqualTo(response.getId());
                    Assertions.assertThat(flightBookingResponse.getFlightNumber()).isEqualTo(response.getFlightNumber());
                    Assertions.assertThat(flightBookingResponse.getUserId()).isEqualTo(response.getUserId());
                });

        BDDMockito.then(flightBookingService).should().create(request);

    }

}
