package com.project.zipsa.repository;

import com.project.zipsa.entity.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointRedisRepository extends CrudRepository<Point, String> {
}
