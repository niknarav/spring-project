package com.example.hotel_booking_systems.model.user;

import com.example.hotel_booking_systems.role.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String password;

    private String email;

    private RoleType role;

}
