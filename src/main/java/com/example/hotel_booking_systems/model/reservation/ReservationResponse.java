package com.example.hotel_booking_systems.model.reservation;

import com.example.hotel_booking_systems.entity.Room;
import com.example.hotel_booking_systems.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private User user;

    private Room room;

}
