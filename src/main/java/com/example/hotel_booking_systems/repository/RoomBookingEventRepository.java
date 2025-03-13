package com.example.hotel_booking_systems.repository;

import com.example.hotel_booking_systems.event.RoomBookingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomBookingEventRepository extends MongoRepository<RoomBookingEvent, Long> {
}
