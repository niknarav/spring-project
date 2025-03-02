package com.example.hotel_booking_systems.service;

import com.example.hotel_booking_systems.entity.Hotel;
import com.example.hotel_booking_systems.mapper.HotelMapper;
import com.example.hotel_booking_systems.model.hotel.HotelResponse;
import com.example.hotel_booking_systems.model.hotel.HotelResponsesList;
import com.example.hotel_booking_systems.model.hotel.UpsertHotelRequest;
import com.example.hotel_booking_systems.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    private final HotelMapper hotelMapper;

    public HotelResponsesList getAllHotels() {
        return hotelMapper.hotelListToHotelResponsesList(hotelRepository.findAll());
    }

    public HotelResponse findHotelById(Long id) {
        return hotelMapper.hotelToResponse(
                hotelRepository.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Hotel not found")
                        )
        );
    }

    public HotelResponse createHotel(UpsertHotelRequest upsertHotelRequest) {
        return hotelMapper.hotelToResponse(
                hotelRepository.save(
                        hotelMapper.requestToHotel(upsertHotelRequest)
                )
        );
    }

    public HotelResponse updateHotel(Long id, UpsertHotelRequest upsertHotelRequest) {
        Hotel existedHotel = hotelRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Hotel not found")
        );
        BeanUtils.copyProperties(upsertHotelRequest, existedHotel);
        return hotelMapper.hotelToResponse(
                hotelRepository.save(existedHotel)
        );
    }

    public void deleteHotelById(Long id) {
        if(!hotelRepository.existsById(id)) {
            throw new EntityNotFoundException("Hotel not found");
        }
        hotelRepository.deleteById(id);
    }


}
