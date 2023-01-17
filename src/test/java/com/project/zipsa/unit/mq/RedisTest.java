package com.project.zipsa.unit.mq;

import com.project.zipsa.config.AWSConfig;
import com.project.zipsa.config.RedisConfig;
import com.project.zipsa.custom.annotation.AllConfigTestAnnotation;
import com.project.zipsa.entity.Point;
import com.project.zipsa.repository.PointRedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@SpringBootTest
@AllConfigTestAnnotation
public class RedisTest {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @Autowired
    private RedisConfig redisConfig;

    @AfterEach
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @Test
    public void 기본_등록_조회기능() {
        String id = "jojoldu";
        LocalDateTime refreshTime = LocalDateTime.of(2018, 5, 26, 0, 0);
        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        pointRedisRepository.save(point);

        Point savedPoint = pointRedisRepository.findById(id).get();
        Assertions.assertThat(savedPoint.getAmount()).isEqualTo(1000L);
        Assertions.assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
    }

    @Test
    public void 수정기능() {
        String id = "jodoldu";
        LocalDateTime refreshTime = LocalDateTime.of(2018, 5, 26, 0, 0);
        pointRedisRepository.save(Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build()
        );

        Point savedPoint = pointRedisRepository.findById(id).get();
        savedPoint.refresh(2000L, LocalDateTime.of(2018, 6, 1, 0, 0));
        pointRedisRepository.save(savedPoint);

        Point refreshPoint = pointRedisRepository.findById(id).get();
        Assertions.assertThat(refreshPoint.getAmount()).isEqualTo(2000L);
    }

    @Test
    public void 하이퍼로그로그_테스트() {
        RedisTemplate<String, Object> template = redisConfig.testRedisTemplate();
        HyperLogLogOperations<String, Object> hyperLogLogOperations = template.opsForHyperLogLog();

        hyperLogLogOperations.add("HLLTEST:1010", 1);
        hyperLogLogOperations.add("HLLTEST:1010", 2);

        Long size = hyperLogLogOperations.size("HLLTEST:1010");

        Assertions.assertThat(size).isEqualTo(2);
    }

}
