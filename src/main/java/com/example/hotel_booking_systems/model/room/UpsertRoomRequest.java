package com.example.hotel_booking_systems.model.room;

import com.example.hotel_booking_systems.entity.Hotel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertRoomRequest {

    private String name;

    private String description;

    private Integer number;

    private Double price;

    private Integer maximumNumberOfPeople;

    private List<LocalDate> unavailableDates;

    private Long hotelId;

}
