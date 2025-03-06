package com.example.hotel_booking_systems.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponsesList {

    private List<ReservationResponse> reservationResponses = new ArrayList<>();

}
