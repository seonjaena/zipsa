package com.project.zipsa.repository.dsl;

import com.project.zipsa.entity.Room;
import com.project.zipsa.entity.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.zipsa.entity.QBuilding.*;
import static com.project.zipsa.entity.QRoom.*;
import static com.project.zipsa.entity.QUsers.*;

@RequiredArgsConstructor
public class RoomDslRepository {

    private final JPAQueryFactory query;
    public List<Room> findBuildingAndRoomByTenant(Users user) {
        return query
                .select(room)
                .from(room)
                .join(room.building)
                .fetchJoin()
                .where(room.tenant.eq(user))
                .fetch();
    }

    public List<Room> findBuildingAndRoomByBuildingMaster(String userId) {
        return query
                .select(room)
                .from(room)
                .join(room.building)
                .fetchJoin()
                .join(building.buildingMaster)
                .where(users.userId.eq(userId))
                .fetch();
    }
}
