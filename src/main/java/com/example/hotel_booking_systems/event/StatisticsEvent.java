package com.example.hotel_booking_systems.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsEvent {
    private String eventType;
    private Long userId;
    private String checkInDate;
    private String checkOutDate;
}
