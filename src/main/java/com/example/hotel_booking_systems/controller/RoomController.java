package com.example.hotel_booking_systems.controller;

import com.example.hotel_booking_systems.entity.Room;
import com.example.hotel_booking_systems.model.room.RoomPageResponse;
import com.example.hotel_booking_systems.model.room.RoomResponse;
import com.example.hotel_booking_systems.model.room.RoomResponsesList;
import com.example.hotel_booking_systems.model.room.UpsertRoomRequest;
import com.example.hotel_booking_systems.service.RoomService;
import com.example.hotel_booking_systems.specification.RoomSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody UpsertRoomRequest upsertRoomRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(upsertRoomRequest));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RoomResponse> updateRoom(@RequestBody UpsertRoomRequest upsertRoomRequest, @PathVariable Long id) {
        return ResponseEntity.ok(roomService.updateRoom(id, upsertRoomRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<RoomPageResponse> searchRooms(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer maxGuests,
            @RequestParam(required = false) LocalDate checkInDate,
            @RequestParam(required = false) LocalDate checkOutDate,
            @RequestParam(required = false) Long hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Specification<Room> spec = RoomSpecification.filterByCriteria(id, title, minPrice, maxPrice, maxGuests, checkInDate, checkOutDate, hotelId);
        RoomPageResponse response = roomService.searchRooms(spec, pageable);
        return ResponseEntity.ok(response);
    }

}
