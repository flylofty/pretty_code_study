package com.lyn.pcode.models.food;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
    boolean existsByRestaurantIdAndName(Long restaurantId, String name);
}
