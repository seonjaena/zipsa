package com.project.zipsa.repository;

import com.project.zipsa.entity.Building;
import com.project.zipsa.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> findAllByBuildingMaster(Users users);

}
