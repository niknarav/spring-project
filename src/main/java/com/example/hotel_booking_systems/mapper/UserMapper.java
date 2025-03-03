package com.example.hotel_booking_systems.mapper;

import com.example.hotel_booking_systems.entity.User;
import com.example.hotel_booking_systems.model.user.UpsertUserRequest;
import com.example.hotel_booking_systems.model.user.UserResponse;
import com.example.hotel_booking_systems.model.user.UserResponsesList;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UpsertUserRequest upsertUserRequest);

    UserResponse userToResponse(User user);

    default UserResponsesList userListToUserResponsesList(List<User> userList) {
        UserResponsesList responses = new UserResponsesList();
        responses.setUserResponses(userList.stream()
                .map(u -> userToResponse(u))
                .toList());

        return responses;
    }
}
