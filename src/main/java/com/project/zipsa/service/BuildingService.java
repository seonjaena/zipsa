package com.project.zipsa.service;

import com.project.zipsa.dto.building.BuildingResponseDto;
import com.project.zipsa.dto.building.SaveBuildingInfoRequestDto;
import com.project.zipsa.entity.Building;
import com.project.zipsa.entity.Room;
import com.project.zipsa.entity.Users;
import com.project.zipsa.repository.BuildingRepository;
import com.project.zipsa.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final UserService userService;
    private final RoomRepository roomRepository;

    public List<BuildingResponseDto> getBuildingInfos(String userId) {
        List<Building> buildings = buildingRepository.findAllByBuildingMaster(userService.getUserNotDeleted(userId));
        return buildings.stream().map(b -> new BuildingResponseDto(b.getBuildingName(), b.getBuildingLocation()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveBuildingInfo(SaveBuildingInfoRequestDto reqDto, String userId) {
        Users user = userService.getUserNotDeleted(userId);
        Building building = Building.of(reqDto.getBuildingName(), user, reqDto.getBuildingLocation());
        Building buildingEntity = buildingRepository.saveAndFlush(building);
        Room room = Room.of(reqDto.getRoomNumber(), reqDto.getRoomContractType(), reqDto.getRoomPrice(), reqDto.getRoomDeposit(), reqDto.getRoomType(), buildingEntity);
        roomRepository.save(room);
    }

}
