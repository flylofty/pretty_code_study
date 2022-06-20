package com.lyn.pcode.models.food;

import com.lyn.pcode.web.dto.food.FoodsDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    boolean existsByRestaurantIdAndName(Long restaurantId, String name);

    // native query 를 이용해서 원하는 원하는 속성만 가져올 수도 있음
    // List<FoodsDto> findIdAndNameAndPriceByRestaurantId(Long restaurantId);
    List<Food> findAllByRestaurantId(Long restaurantId);
}
