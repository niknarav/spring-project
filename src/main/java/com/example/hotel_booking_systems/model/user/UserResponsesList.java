package com.example.hotel_booking_systems.model.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponsesList {

    private List<UserResponse> userResponses = new ArrayList<>();

}
