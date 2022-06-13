package com.lyn.pcode.model.entity;

import com.lyn.pcode.model.Timestamped;

import javax.persistence.*;

@Entity
public class Restaurant extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int minOrderPrice;

    @Column(nullable = false)
    private int deliveryFee;
}
