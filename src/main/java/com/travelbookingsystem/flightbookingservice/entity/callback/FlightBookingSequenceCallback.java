package com.travelbookingsystem.flightbookingservice.entity.callback;

import com.travelbookingsystem.flightbookingservice.entity.FlightBooking;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.lang.NonNull;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightBookingSequenceCallback implements BeforeConvertCallback<FlightBooking> {

    DatabaseClient databaseClient;

    @NonNull
    @Override
    public Publisher<FlightBooking> onBeforeConvert(FlightBooking flightBooking, @NonNull SqlIdentifier table) {
        if (flightBooking.getId() == null) {
            return databaseClient
                    .sql("SELECT nextval('seq_flight_booking_id')")
                    .map((row, metadata) -> row.get(0, Long.class))
                    .first()
                    .map(id -> {
                        flightBooking.setId(id);
                        return flightBooking;
                    });
        }
        return Mono.just(flightBooking);
    }
}
