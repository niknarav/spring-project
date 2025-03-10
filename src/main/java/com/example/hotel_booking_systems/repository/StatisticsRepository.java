package com.example.hotel_booking_systems.repository;

import com.example.hotel_booking_systems.entity.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends MongoRepository<Statistics, Long> {
}
