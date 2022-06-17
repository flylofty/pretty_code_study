package com.lyn.pcode.models.restaurant;

import com.lyn.pcode.models.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Food extends Timestamped {

    // Food Entity 실질적인 id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 레스토랑 아이디, 외래키 지정하면 좋을 것 같다
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
