package com.project.zipsa.repository;

import com.project.zipsa.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomIdx(Long roomIdx);

}
