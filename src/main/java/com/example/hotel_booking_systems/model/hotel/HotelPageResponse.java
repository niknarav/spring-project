package com.example.hotel_booking_systems.model.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelPageResponse {
    private List<HotelResponse> hotels;
    private long totalElements;
}
