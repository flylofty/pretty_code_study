package com.lyn.pcode.models.order;

import com.lyn.pcode.models.Timestamped;
import com.lyn.pcode.models.food.Food;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "orders")
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원 기능이 없으므로 우선은 UUID를 이용
    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private Long foodId;

    @Column(nullable = false)
    private Integer quantity;

    public Order(String orderId, Food food, Integer quantity) {
        this.orderId = orderId;
        this.restaurantId = food.getRestaurantId();
        this.foodId = food.getId();
        this.quantity = quantity;
    }
}
