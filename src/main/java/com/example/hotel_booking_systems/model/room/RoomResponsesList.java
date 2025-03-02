package com.example.hotel_booking_systems.model.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponsesList {

    private List<RoomResponse> roomResponses = new ArrayList<>();

}
