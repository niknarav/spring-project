package com.example.hotel_booking_systems.model.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomPageResponse {
    private List<RoomResponse> rooms;
    private long totalElements;
}
