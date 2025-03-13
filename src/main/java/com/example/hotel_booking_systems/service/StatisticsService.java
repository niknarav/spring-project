package com.example.hotel_booking_systems.service;

import com.example.hotel_booking_systems.event.RoomBookingEvent;
import com.example.hotel_booking_systems.event.StatisticsEvent;
import com.example.hotel_booking_systems.event.UserRegistrationEvent;
import com.example.hotel_booking_systems.repository.RoomBookingEventRepository;
import com.example.hotel_booking_systems.repository.StatisticsRepository;
import com.example.hotel_booking_systems.repository.UserRegistrationEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRegistrationEventRepository userRegistrationEventRepository;

    private final RoomBookingEventRepository roomBookingEventRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void saveUserRegistrationEvent(UserRegistrationEvent event) {
        userRegistrationEventRepository.save(event);
        kafkaTemplate.send("user_registration_topic", event);
    }

    public void saveRoomBookingEvent(RoomBookingEvent event) {
        roomBookingEventRepository.save(event);
        kafkaTemplate.send("room_booking_topic", event);
    }

    public void exportToCSV(String filePath) throws IOException {
        List<UserRegistrationEvent> userEvents = userRegistrationEventRepository.findAll();
        List<RoomBookingEvent> roomEvents = roomBookingEventRepository.findAll();

        log.info("Number of User Registration Events: {}", userEvents.size());
        log.info("Number of Room Booking Events: {}", roomEvents.size());

        File file = new File(filePath);
        if (file.getParentFile() != null) {
            boolean dirsCreated = file.getParentFile().mkdirs();
            log.info("Directories created: {}", dirsCreated);
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.append("EventType,UserId,CheckInDate,CheckOutDate\n");
            for (UserRegistrationEvent event : userEvents) {
                writer.append("UserRegistration,")
                        .append(event.getUserId().toString()).append(",")
                        .append("null,null\n");
            }
            for (RoomBookingEvent event : roomEvents) {
                writer.append("RoomBooking,")
                        .append(event.getUserId().toString()).append(",")
                        .append(event.getCheckInDate()).append(",")
                        .append(event.getCheckOutDate()).append("\n");
            }
            writer.flush();
            log.info("CSV file created successfully at: {}", file.getAbsolutePath());
        }
    }
}
