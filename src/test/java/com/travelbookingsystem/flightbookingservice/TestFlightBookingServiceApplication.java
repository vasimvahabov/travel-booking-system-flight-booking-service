package com.travelbookingsystem.flightbookingservice;

import org.springframework.boot.SpringApplication;

public class TestFlightBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(FlightBookingServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
