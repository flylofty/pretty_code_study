package com.lyn.pcode.models.order;

import com.lyn.pcode.models.food.Food;

import javax.persistence.*;

@Entity
public class OrderFood {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_FOOD_ID")
    private Long id;

    @ManyToOne // FK, 외래키가 있는 곳이 연관관계의 주인이 된다.
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @OneToOne
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @Column(name = "ORDER_FOOD_QUANTITY", nullable = false)
    private Integer quantity;
}
