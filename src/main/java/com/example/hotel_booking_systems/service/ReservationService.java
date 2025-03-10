package com.example.hotel_booking_systems.service;

import com.example.hotel_booking_systems.entity.Reservation;
import com.example.hotel_booking_systems.event.ReservationEvent;
import com.example.hotel_booking_systems.mapper.ReservationMapper;
import com.example.hotel_booking_systems.model.reservation.ReservationResponse;
import com.example.hotel_booking_systems.model.reservation.ReservationResponsesList;
import com.example.hotel_booking_systems.model.reservation.UpsertReservationRequest;
import com.example.hotel_booking_systems.repository.ReservationRepository;
import com.example.hotel_booking_systems.repository.RoomRepository;
import com.example.hotel_booking_systems.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final KafkaTemplate<String, ReservationEvent> kafkaReservationTemplate;

    public ReservationResponse create(UpsertReservationRequest request) {
        Reservation reservation = reservationMapper.requestToReservation(request);
        reservation.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format("User by id not found. Id: {0}", request.getUserId())
                )));
        reservation.setRoom(roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format("Room by id not found. Id: {0}", request.getRoomId())
                )));

        ReservationEvent event = new ReservationEvent(reservation.getUser ().getId(), reservation.getCheckInDate(), reservation.getCheckOutDate());
        kafkaReservationTemplate.send("reservation", event);

        return reservationMapper.reservationToResponse(reservationRepository.save(reservation));
    }

    public ReservationResponsesList getAll() {
        return reservationMapper.reservationsListToReservationResponsesList(
                reservationRepository.findAll()
        );
    }

}
