package com.example.hotel_booking_systems.controller;

import com.example.hotel_booking_systems.model.hotel.HotelResponse;
import com.example.hotel_booking_systems.model.hotel.HotelResponsesList;
import com.example.hotel_booking_systems.model.hotel.UpsertHotelRequest;
import com.example.hotel_booking_systems.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotel")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/all")
    public ResponseEntity<HotelResponsesList> getAll() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HotelResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.findHotelById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<HotelResponse> create(@RequestBody UpsertHotelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelService.createHotel(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id, @RequestBody UpsertHotelRequest request) {
        return ResponseEntity.ok(hotelService.updateHotel(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hotelService.deleteHotelById(id);
        return ResponseEntity.noContent().build();
    }
}
