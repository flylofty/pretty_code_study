package com.lyn.pcode.models.food;

import com.lyn.pcode.models.Timestamped;
import com.lyn.pcode.models.restaurant.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Food extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOOD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @Column(nullable = false, name = "FOOD_NAME")
    private String name;

    @Column(nullable = false, name = "FOOD_PRICE")
    private String price;

    @Builder
    public Food(Restaurant restaurant, String name, String price) {
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
    }

//    @Builder
//    public Food(Long restaurantId, String name, String price) {
//        this.restaurantId = restaurantId;
//        this.name = name;
//        this.price = price;
//    }
}
