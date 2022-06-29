package com.lyn.pcode.models.order;

import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.web.dto.order.FoodOrderInfoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class OrderFood {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_FOOD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // FK, 외래키가 있는 곳이 연관관계의 주인이 된다.
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @OneToOne
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @Column(name = "ORDER_FOOD_QUANTITY", nullable = false)
    private Integer quantity;

    public OrderFood(Order order, Food food, FoodOrderInfoDto dto) {
        this.order = order;
        this.food = food;
        this.quantity = dto.getQuantity();
    }
}
