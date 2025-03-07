package com.example.hotel_booking_systems.specification;

import com.example.hotel_booking_systems.entity.Hotel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class HotelSpecification {

    public static Specification<Hotel> filterByCriteria(Long id, String name, String header, String city, String address, String distance, Double rating, Integer numberOfRatings) {
        return (Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (id != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), id));
            }
            if (name != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (header != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("header"), "%" + header + "%"));
            }
            if (city != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("city"), "%" + city + "%"));
            }
            if (address != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }
            if (distance != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("distanceFromTheCityCenter"), "%" + distance + "%"));
            }
            if (rating != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("rating"), rating));
            }
            if (numberOfRatings != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("numberOfRatings"), numberOfRatings));
            }

            return predicate;
        };
    }
}
