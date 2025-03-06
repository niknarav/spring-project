package com.example.hotel_booking_systems.mapper;

import com.example.hotel_booking_systems.entity.Hotel;
import com.example.hotel_booking_systems.model.hotel.HotelResponse;
import com.example.hotel_booking_systems.model.hotel.HotelResponsesList;
import com.example.hotel_booking_systems.model.hotel.UpsertHotelRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel requestToHotel(UpsertHotelRequest request);

    Hotel requestToHotel(Long id, UpsertHotelRequest request);

    HotelResponse hotelToResponse(Hotel hotel);

    default HotelResponsesList hotelListToHotelResponsesList(List<Hotel> hotels) {
        HotelResponsesList hotelResponsesList = new HotelResponsesList();

        hotelResponsesList.setHotelResponses(hotels.stream()
                .map(this::hotelToResponse)
                .toList());

        return hotelResponsesList;
    }
}
