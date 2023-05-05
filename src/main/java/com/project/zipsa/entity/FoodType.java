package com.project.zipsa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FOOD_TYPE")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(of = {"id", "foodTypeName", "foodOrder"})
public class FoodType {

    @Id
    @Column(name = "FOOD_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "FOOD_TYPE_NAME")
    private String foodTypeName;

    @Column(name = "FOOD_ORDER")
    private int foodOrder;

    @OneToMany(mappedBy = "foodType")
    List<FoodStore> foodStoreList = new ArrayList<>();

    public FoodType(String foodTypeName, int foodOrder) {
        this.foodTypeName = foodTypeName;
        this.foodOrder = foodOrder;
    }

}
