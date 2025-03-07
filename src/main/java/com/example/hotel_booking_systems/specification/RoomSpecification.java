package com.example.hotel_booking_systems.specification;

import com.example.hotel_booking_systems.entity.Room;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RoomSpecification {

    public static Specification<Room> filterByCriteria(Long id, String title, Double minPrice, Double maxPrice,
                                                       Integer maxGuests, LocalDate checkInDate, LocalDate checkOutDate, Long hotelId) {
        return (Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (id != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), id));
            }
            if (title != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + title + "%"));
            }
            if (minPrice != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (maxGuests != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("maximumNumberOfPeople"), maxGuests));
            }
            if (hotelId != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("hotel").get("id"), hotelId));
            }

            if (checkInDate != null && checkOutDate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.not(root.join("unavailableDates").in(checkInDate, checkOutDate)));
            }

            return predicate;
        };
    }

}
