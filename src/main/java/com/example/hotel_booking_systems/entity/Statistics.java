package com.example.hotel_booking_systems.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "statistics")
public class Statistics {

    @Id
    private Long id;

    private Long userId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

}
