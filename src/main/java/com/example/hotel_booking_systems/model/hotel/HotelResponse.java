package com.example.hotel_booking_systems.model.hotel;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {

    private Long id;

    private String name;

    private String header;

    private String city;

    private String address;

    private String distanceFromTheCityCenter;

    private Double rating;

    private Integer numberOfRatings;

}
