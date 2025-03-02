package com.example.hotel_booking_systems.controller;

import com.example.hotel_booking_systems.model.room.RoomResponse;
import com.example.hotel_booking_systems.model.room.RoomResponsesList;
import com.example.hotel_booking_systems.model.room.UpsertRoomRequest;
import com.example.hotel_booking_systems.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/all")
    public ResponseEntity<RoomResponsesList> getAllRooms() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<RoomResponse> createRoom(@RequestBody UpsertRoomRequest upsertRoomRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(upsertRoomRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@RequestBody UpsertRoomRequest upsertRoomRequest, @PathVariable Long id) {
        return ResponseEntity.ok(roomService.updateRoom(id, upsertRoomRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
