package com.example.hotel_booking_systems.controller;

import com.example.hotel_booking_systems.model.reservation.ReservationResponse;
import com.example.hotel_booking_systems.model.reservation.ReservationResponsesList;
import com.example.hotel_booking_systems.model.reservation.UpsertReservationRequest;
import com.example.hotel_booking_systems.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ReservationResponsesList> getAllReservations(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody UpsertReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.create(request));
    }

}
