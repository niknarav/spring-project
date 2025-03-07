package com.example.hotel_booking_systems.controller;

import com.example.hotel_booking_systems.entity.Hotel;
import com.example.hotel_booking_systems.model.hotel.HotelPageResponse;
import com.example.hotel_booking_systems.model.hotel.HotelResponse;
import com.example.hotel_booking_systems.model.hotel.HotelResponsesList;
import com.example.hotel_booking_systems.model.hotel.UpsertHotelRequest;
import com.example.hotel_booking_systems.service.HotelService;
import com.example.hotel_booking_systems.specification.HotelSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<HotelResponse> create(@RequestBody UpsertHotelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelService.createHotel(request));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id, @RequestBody UpsertHotelRequest request) {
        return ResponseEntity.ok(hotelService.updateHotel(id, request));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        hotelService.deleteHotelById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/updateRating/{id}")
    public ResponseEntity<HotelResponse> rating(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestParam Double newMark, @PathVariable Long id) {
        HotelResponse updatedHotel = hotelService.updateRating(id, newMark);
        return ResponseEntity.ok(updatedHotel);
    }

    @GetMapping("/search")
    public ResponseEntity<HotelPageResponse> searchHotels(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String header,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String distance,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Integer numberOfRatings,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Specification<Hotel> spec = HotelSpecification.filterByCriteria(id, name, header, city, address, distance, rating, numberOfRatings);
        HotelPageResponse response = hotelService.searchHotels(spec, pageable);
        return ResponseEntity.ok(response);
    }
}
