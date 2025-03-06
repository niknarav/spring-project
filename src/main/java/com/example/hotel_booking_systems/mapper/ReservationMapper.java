package com.example.hotel_booking_systems.mapper;

import com.example.hotel_booking_systems.entity.Reservation;
import com.example.hotel_booking_systems.model.reservation.ReservationResponse;
import com.example.hotel_booking_systems.model.reservation.ReservationResponsesList;
import com.example.hotel_booking_systems.model.reservation.UpsertReservationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    default Reservation requestToReservation(UpsertReservationRequest request) {
       return Reservation.builder()
               .checkInDate(request.getCheckInDate())
               .checkOutDate(request.getCheckOutDate())
               .build();
    }

    ReservationResponse reservationToResponse(Reservation reservation);

    default ReservationResponsesList reservationsListToReservationResponsesList(List<Reservation> reservations) {
        ReservationResponsesList responses = new ReservationResponsesList();

        responses.setReservationResponses(reservations.stream()
                        .map(this::reservationToResponse)
                        .toList());

        return responses;
    }

}
