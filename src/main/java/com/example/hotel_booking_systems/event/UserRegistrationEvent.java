package com.example.hotel_booking_systems.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_registration_events")
public class UserRegistrationEvent {
    @Id
    private ObjectId id;
    private Long userId;
}