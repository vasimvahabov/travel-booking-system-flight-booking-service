package com.travelbookingsystem.flightbookingservice.entity.converter;

import com.travelbookingsystem.flightbookingservice.entity.constant.FlightBookingStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

@ReadingConverter
public class FlightBookingStatusConverterReading implements Converter<Integer, FlightBookingStatus> {

    @Override
    public FlightBookingStatus convert(@NonNull Integer source) {
        return FlightBookingStatus.fromValue(source);
    }

}
