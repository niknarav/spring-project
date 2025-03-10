package com.example.hotel_booking_systems.service;

import com.example.hotel_booking_systems.entity.Statistics;
import com.example.hotel_booking_systems.event.RegistrationEvent;
import com.example.hotel_booking_systems.event.ReservationEvent;
import com.example.hotel_booking_systems.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @KafkaListener(topics = "${app.kafka.registration.kafkaRegisterTopic}", groupId = "${app.kafka.registration.kafkaRegisterGroupId}")
    public void handleRegistrationEvent(RegistrationEvent event) {
        if (event.getUserId() == null) {
            throw new IllegalArgumentException("User  ID cannot be null");
        }
        Statistics statistics = new Statistics();
        statistics.setUserId(event.getUserId());
        statisticsRepository.save(statistics);
    }

    @KafkaListener(topics = "${app.kafka.reservation.kakfaReservationTopic}", groupId = "${app.kafka.reservation.kafkaReservationGroupId}")
    public void handleReservationEvent(ReservationEvent event) {
        Statistics statistics = new Statistics();
        statistics.setUserId(event.getUserId());
        statistics.setCheckInDate(event.getCheckInDate());
        statistics.setCheckOutDate(event.getCheckOutDate());
        statisticsRepository.save(statistics);
    }

    public void exportStatisticsToCSV(String filePath) throws IOException {
        List<Statistics> statisticsList = statisticsRepository.findAll();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("User Id,CheckInDate,CheckOutDate\n");
            for (Statistics statistics : statisticsList) {
                writer.append(String.valueOf(statistics.getUserId()))
                        .append(",")
                        .append(statistics.getCheckInDate() != null ? statistics.getCheckInDate().toString() : "")
                        .append(",")
                        .append(statistics.getCheckOutDate() != null ? statistics.getCheckOutDate().toString() : "")
                        .append("\n");
            }
        }
    }
}
