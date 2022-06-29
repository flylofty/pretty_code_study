package com.lyn.pcode.models.order;

import com.lyn.pcode.models.Timestamped;
import com.lyn.pcode.models.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @OneToMany(fetch = LAZY, mappedBy = "order") // NullPointerException 방지를 위해 객체 생성
    List<OrderFood> OrderFoods = new ArrayList<>();

    public Order(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
