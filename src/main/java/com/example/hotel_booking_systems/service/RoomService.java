package com.example.hotel_booking_systems.service;

import com.example.hotel_booking_systems.entity.Room;
import com.example.hotel_booking_systems.mapper.RoomMapper;
import com.example.hotel_booking_systems.model.room.RoomResponse;
import com.example.hotel_booking_systems.model.room.RoomResponsesList;
import com.example.hotel_booking_systems.model.room.UpsertRoomRequest;
import com.example.hotel_booking_systems.repository.HotelRepository;
import com.example.hotel_booking_systems.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    private final HotelRepository hotelRepository;

    public RoomResponsesList findAll() {
        return roomMapper.roomListToRoomResponsesList(roomRepository.findAll());
    }

    public RoomResponse findById(Long id) {
        return roomMapper.roomToResponse(roomRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Room not found")
        ));
    }

    public RoomResponse createRoom(UpsertRoomRequest request) {
        Room savedRoom = roomMapper.requestToRoom(request);
        savedRoom.setHotel(hotelRepository.findById(request.getHotelId()).orElseThrow(
                () -> new EntityNotFoundException("Hotel not found")
        ));
        roomRepository.save(savedRoom);
        return roomMapper.roomToResponse(savedRoom);
    }

    public RoomResponse updateRoom(Long id, UpsertRoomRequest request) {
        Room existingRoom = roomRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Room not found")
        );

        BeanUtils.copyProperties(request, existingRoom);
        return roomMapper.roomToResponse(roomRepository.save(existingRoom));
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

}
