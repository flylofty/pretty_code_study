package com.lyn.pcode.service;

import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.web.dto.food.SaveFoodDto;
import com.lyn.pcode.web.dto.food.SaveFoodRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest // 이 어노테이션을 통해 H2 데이터베이스를 자동으로 실행함
class FoodServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodRepository foodRepository;

    @Test
    @DisplayName("음식점 메뉴 등록")
    void saveFoodsTest() throws Exception {

        // given
        Restaurant restaurant = Restaurant.builder()
                .name("쉐이크쉑 청담점")
                .minOrderPrice(5000)
                .deliveryFee(2000)
                .build();

        restaurantRepository.save(restaurant);
//        em.flush();
//        em.clear();
        restaurant = restaurantRepository.findAll().get(0);

        final Long restaurantId = restaurant.getId();

        List<SaveFoodDto> foods = new ArrayList<>();
        foods.add(new SaveFoodDto("쉑버거 더블", 10900));
        foods.add(new SaveFoodDto("치즈 감자튀김", 4900));
        foods.add(new SaveFoodDto("쉐이크", 5900));
        SaveFoodRequestDto request = new SaveFoodRequestDto(foods);

        foodService.saveFoods(request, restaurantId);
//        em.flush();
//        em.clear();

        List<SaveFoodDto> foods2 = new ArrayList<>();
        foods.add(new SaveFoodDto("쉑버거 더블2", 10900));
        foods.add(new SaveFoodDto("치즈 감자튀김2", 4900));
        foods.add(new SaveFoodDto("쉐이크", 5900));
        SaveFoodRequestDto request2 = new SaveFoodRequestDto(foods2);


        try {
            foodService.saveFoods(request, restaurantId);
        } catch (Exception e) {
            System.out.println("====================");
            System.out.println(e.getMessage());
        }

        List<SaveFoodDto> foods3 = new ArrayList<>();
        foods.add(new SaveFoodDto("쉑버거 더블3", 11900));
        foods.add(new SaveFoodDto("치즈 감자튀김3", 5900));
        foods.add(new SaveFoodDto("쉐이크3", 6900));
        SaveFoodRequestDto request3 = new SaveFoodRequestDto(foods3);

        foodService.saveFoods(request3, restaurantId);

        try {
            foodService.saveFoods(request, restaurantId);
        } catch (Exception e) {
            System.out.println("====================");
            System.out.println(e.getMessage());
        }

        System.out.println(10);
    }
}