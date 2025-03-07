package com.example.hotel_booking_systems.service;

import com.example.hotel_booking_systems.entity.Hotel;
import com.example.hotel_booking_systems.mapper.HotelMapper;
import com.example.hotel_booking_systems.model.hotel.HotelPageResponse;
import com.example.hotel_booking_systems.model.hotel.HotelResponse;
import com.example.hotel_booking_systems.model.hotel.HotelResponsesList;
import com.example.hotel_booking_systems.model.hotel.UpsertHotelRequest;
import com.example.hotel_booking_systems.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

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
                                () -> new EntityNotFoundException(
                                        MessageFormat.format("Hotel by id not found, Id: {0}", id)
                                )
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
                () -> new EntityNotFoundException(
                        MessageFormat.format("Hotel by id not found, Id: {0}", id)
                )
        );
        BeanUtils.copyProperties(upsertHotelRequest, existedHotel);
        return hotelMapper.hotelToResponse(
                hotelRepository.save(existedHotel)
        );
    }

    public void deleteHotelById(Long id) {
        if(!hotelRepository.existsById(id)) {
            throw new EntityNotFoundException( MessageFormat.format("Hotel by id not found, Id: {0}", id));
        }
        hotelRepository.deleteById(id);
    }

    public HotelResponse updateRating(Long hotelId, Double newMark) {
        if (newMark < 1 || newMark > 5) {
            throw new IllegalArgumentException("Оценка должна быть в диапазоне от 1 до 5.");
        }

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Hotel by id not found, Id: {0}", hotelId)
                ));

        Double currentRating = hotel.getRating() != null ? hotel.getRating() : 0.0;
        Integer numberOfRatings = hotel.getNumberOfRatings() != null ? hotel.getNumberOfRatings() : 0;

        if (numberOfRatings == 0) {
            hotel.setRating(newMark);
            hotel.setNumberOfRatings(1);
        } else {
            Double totalRating = currentRating * (double) numberOfRatings;
            totalRating = totalRating - currentRating + newMark;
            hotel.setRating(Math.round((totalRating / (double) numberOfRatings) * 10.0) / 10.0);
            hotel.setNumberOfRatings(numberOfRatings + 1);
        }

        hotelRepository.save(hotel);
        return hotelMapper.hotelToResponse(hotel);
    }

    public HotelPageResponse searchHotels(Specification<Hotel> spec, Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findAll(spec, pageable);
        List<HotelResponse> hotelResponses = hotelPage.getContent().stream()
                .map(hotelMapper::hotelToResponse)
                .collect(Collectors.toList());

        return new HotelPageResponse(hotelResponses, hotelPage.getTotalElements());
    }


}
