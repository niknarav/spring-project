package com.example.hotel_booking_systems.repository;

import com.example.hotel_booking_systems.event.StatisticsEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<StatisticsEvent, Long> {
}
