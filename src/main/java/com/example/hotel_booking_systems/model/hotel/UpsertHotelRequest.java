package com.example.hotel_booking_systems.model.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertHotelRequest {

    private String name;

    private String header;

    private String city;

    private String address;

    private Integer distanceFromTheCityCenter;

}
