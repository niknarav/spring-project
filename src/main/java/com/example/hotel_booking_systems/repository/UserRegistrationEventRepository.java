package com.example.hotel_booking_systems.repository;

import com.example.hotel_booking_systems.event.UserRegistrationEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRegistrationEventRepository extends MongoRepository<UserRegistrationEvent, Long> {
}
