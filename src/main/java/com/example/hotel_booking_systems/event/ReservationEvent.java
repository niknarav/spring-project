package com.example.hotel_booking_systems.event;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEvent {

    private Long userId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

}
