package com.travelbookingsystem.flightbookingservice.entity.converter;

import com.travelbookingsystem.flightbookingservice.entity.constant.FlightBookingStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class FlightBookingStatusConverterWriting implements Converter<FlightBookingStatus, Integer>{

    @Override
    public Integer convert(FlightBookingStatus source) {
        return source.getValue();
    }

}
