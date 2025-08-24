package com.travelbookingsystem.flightbookingservice.entity.converter;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import java.util.ArrayList;

@Configuration
public class FlightBookingStatusConverter {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(ConnectionFactory connectionFactory) {
        var dialect = DialectResolver.getDialect(connectionFactory);
        var converters = new ArrayList<Converter<?, ?>>();
        converters.add(new FlightBookingStatusConverterReading());
        converters.add(new FlightBookingStatusConverterWriting());
        return R2dbcCustomConversions.of(dialect, converters);
    }


}
