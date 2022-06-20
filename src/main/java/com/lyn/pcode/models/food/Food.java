package com.lyn.pcode.models.food;

import com.lyn.pcode.models.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Food extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String price;

    @Builder
    public Food(Long restaurantId, String name, String price) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
    }
}
