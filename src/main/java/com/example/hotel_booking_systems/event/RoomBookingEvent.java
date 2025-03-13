package com.example.hotel_booking_systems.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "room_booking_events")
public class RoomBookingEvent {
    @Id
    private ObjectId id;
    private Long userId;
    private String checkInDate;
    private String checkOutDate;
}
