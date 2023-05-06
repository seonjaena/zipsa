package com.project.zipsa.repository;

import com.project.zipsa.entity.Room;
import com.project.zipsa.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomIdx(Long roomIdx);

}
