package com.example.hotel_booking_systems.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String header;

    private String city;

    private String address;

    @Column(name = "distance")
    private String distanceFromTheCityCenter;

    private Double rating = 0.0;

    @Column(name = "number_of_ratings")
    private Integer numberOfRatings = 0;

}
