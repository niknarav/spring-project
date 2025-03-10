package com.example.hotel_booking_systems.service;

import com.example.hotel_booking_systems.entity.User;
import com.example.hotel_booking_systems.event.RegistrationEvent;
import com.example.hotel_booking_systems.mapper.UserMapper;
import com.example.hotel_booking_systems.model.user.UpsertUserRequest;
import com.example.hotel_booking_systems.model.user.UserResponse;
import com.example.hotel_booking_systems.model.user.UserResponsesList;
import com.example.hotel_booking_systems.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final KafkaTemplate<String, RegistrationEvent> kafkaRegistrationTemplate;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format("User by username not found. Username: {0}", username)
                ));
    }

    public UserResponsesList getAllUsers() {
        return userMapper.userListToUserResponsesList(
                userRepository.findAll()
        );
    }

    public UserResponse getUserById(Long id) {
        return userMapper.userToResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(
                                MessageFormat.format("User by id not found. Id: {0}", id)
                        ))
        );
    }

    public UserResponse createUser(UpsertUserRequest upsertUserRequest) {
        upsertUserRequest.setPassword(passwordEncoder.encode(upsertUserRequest.getPassword()));
        RegistrationEvent event = new RegistrationEvent(userMapper.requestToUser(upsertUserRequest).getId());
        User createdUser = userRepository.save(userMapper.requestToUser(upsertUserRequest));
        kafkaRegistrationTemplate.send("register-topic", event);
        return userMapper.userToResponse(createdUser);
    }

    public UserResponse updateUser(Long id, UpsertUserRequest upsertUserRequest) {
        User existedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format("User by id not found. Id: {0}", id)
                ));
        BeanUtils.copyProperties(upsertUserRequest, existedUser);
        existedUser.setPassword(passwordEncoder.encode(upsertUserRequest.getPassword()));
        return userMapper.userToResponse(userRepository.save(existedUser));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
