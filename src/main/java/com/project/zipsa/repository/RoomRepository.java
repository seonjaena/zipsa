package com.project.zipsa.repository;

import com.project.zipsa.entity.Room;
import com.project.zipsa.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

//    @Query("SELECT r FROM Room r " +
//            "JOIN FETCH r.building b " +
//            "WHERE r.tenant = :user")
//    List<Room> findAllByTenant(Users user);
//
//    @Query("SELECT r FROM Room r " +
//            "JOIN FETCH r.building b " +
//            "JOIN b.buildingMaster m " +
//            "WHERE m.userId = :userId")
//    List<Room> findAllByBuildingMaster(@Param(value = "userId") String userId);

    Optional<Room> findByRoomIdx(Long roomIdx);

}
