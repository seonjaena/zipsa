package com.project.zipsa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "FOOD_STORE")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(of = {"id", "storeName", "rate", "ownerName"})
public class FoodStore {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "STORE_NAME")
    private String storeName;
    @Column(name = "RATE")
    private int rate;
    @Column(name = "OWNER_NAME")
    private String ownerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_TYPE_ID")
    private FoodType foodType;

    public FoodStore(String storeName, int rate, String ownerName, FoodType foodType) {
        this.storeName = storeName;
        this.rate = rate;
        this.ownerName = ownerName;
        changeFoodType(foodType);
    }

    private void changeFoodType(FoodType foodType) {
        this.foodType = foodType;
        foodType.getFoodStoreList().add(this);
    }

}
