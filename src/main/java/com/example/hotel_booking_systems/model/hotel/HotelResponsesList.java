package com.example.hotel_booking_systems.model.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponsesList {

    private List<HotelResponse> hotelResponses = new ArrayList<>();

}
