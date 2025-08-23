package com.travelbookingsystem.flightbookingservice.mapper;

import com.travelbookingsystem.flightbookingservice.dto.request.FlightBookingRequest;
import com.travelbookingsystem.flightbookingservice.dto.response.FlightBookingResponse;
import com.travelbookingsystem.flightbookingservice.entity.FlightBooking;
import com.travelbookingsystem.flightbookingservice.entity.constant.FlightBookingStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FlightBookingMapper {

    FlightBookingResponse entityToResponse(FlightBooking flightBooking);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "lastModifiedDateTime", ignore = true)
    @Mapping(target = "flightNumber", source = "request.flightNumber")
    @Mapping(target = "userId", source = "request.userId")
    FlightBooking requestToEntity(FlightBookingRequest request, FlightBookingStatus status);

}
