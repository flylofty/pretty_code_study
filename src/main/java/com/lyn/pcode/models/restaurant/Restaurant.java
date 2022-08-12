package com.lyn.pcode.models.restaurant;

import com.lyn.pcode.models.Timestamped;
import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.models.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESTAURANT_ID")
    private Long id;

    @Column(nullable = false, name = "RESTAURANT_NAME", unique = true)
    private String name;

    @Column(nullable = false, name = "RESTAURANT_MIN_ORDER_PRICE")
    private Integer minOrderPrice;

    @Column(nullable = false, name = "RESTAURANT_DELIVERY_FEE")
    private Integer deliveryFee;

    @OneToMany(mappedBy = "restaurant", fetch = LAZY)
    List<Order> Orders = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", fetch = LAZY)
    List<Food> menu = new ArrayList<>();

    @Builder
    public Restaurant(String name, int minOrderPrice, int deliveryFee) {
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }
}
