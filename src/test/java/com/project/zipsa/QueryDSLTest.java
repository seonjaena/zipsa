package com.project.zipsa;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.project.zipsa.config.QueryDslConfig;
import com.project.zipsa.config.dev.P6spyConfig;
import com.project.zipsa.entity.FoodStore;
import com.project.zipsa.entity.FoodType;
import com.project.zipsa.entity.QFoodStore;
import com.project.zipsa.repository.FoodStoreRepository;
import com.project.zipsa.repository.FoodTypeRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest(showSql = false)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import({QueryDslConfig.class, P6spyConfig.class})
public class QueryDSLTest {

    @Autowired
    private FoodTypeRepository foodTypeRepository;
    @Autowired
    private FoodStoreRepository foodStoreRepository;
    @Autowired
    private JPAQueryFactory query;

    @BeforeEach
    public void setData() {
        FoodType korean = new FoodType("한식", 1);
        FoodType western = new FoodType("양식", 2);
        FoodType chinese = new FoodType("중식", 3);

        foodTypeRepository.saveAll(List.of(korean, western, chinese));

        FoodStore foodStore1 = new FoodStore("삼겹살", 9, "sangmessi", korean);
        FoodStore foodStore2 = new FoodStore("닭갈비", 2, "sangmessi", korean);
        FoodStore foodStore3 = new FoodStore("부대찌개", 3, "lake", korean);
        FoodStore foodStore4 = new FoodStore("순대국밥", 4, "lake", korean);
        FoodStore foodStore5 = new FoodStore("소고기", 5, "lake", korean);
        FoodStore foodStore6 = new FoodStore("스파게티", 6, "sangmessi", western);
        FoodStore foodStore7 = new FoodStore("피자", 7, "sangmessi", western);
        FoodStore foodStore8 = new FoodStore("중국집", 8, "hong", chinese);
        FoodStore foodStore9 = new FoodStore("중국집2", 9, "hong", chinese);
        FoodStore foodStore10 = new FoodStore("중국집3", 10, "hong", chinese);

        foodStoreRepository.saveAll(List.of(foodStore1, foodStore2, foodStore3, foodStore4, foodStore5, foodStore6, foodStore7, foodStore8, foodStore9, foodStore10));

        foodStoreRepository.findAll().forEach(fs -> System.out.println(fs.getStoreName()));
    }

    @Test
    void 기본쿼리() {
        List<FoodStore> results = query
                .selectFrom(QFoodStore.foodStore)
                .fetch();

        Assertions.assertThat(results.size()).isSameAs(10);
    }

    @Test
    void 기본쿼리_조건절() {
        List<FoodStore> results = query
                .selectFrom(QFoodStore.foodStore)
                .where(
                        QFoodStore.foodStore.rate.goe(5)
                                .and(QFoodStore.foodStore.storeName.startsWith("삼"))
                                .and(QFoodStore.foodStore.storeName.contains("겹"))
                                .and(QFoodStore.foodStore.storeName.endsWith("살"))
                )
                .orderBy(
                        QFoodStore.foodStore.rate.desc(),
                        QFoodStore.foodStore.storeName.asc()
                )
                .fetch();

        Assertions.assertThat(results.size()).isSameAs(1);
    }

    @Test
    void 기본쿼리_페이징() {
        JPAQuery<FoodStore> fetchResult = query
                .selectFrom(QFoodStore.foodStore)
                .offset(0)
                .limit(3)
                .fetchAll();
    }

}
