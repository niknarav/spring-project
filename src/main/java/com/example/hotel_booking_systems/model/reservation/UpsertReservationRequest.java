package com.example.hotel_booking_systems.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertReservationRequest {

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Long userId;

    private Long roomId;

}
