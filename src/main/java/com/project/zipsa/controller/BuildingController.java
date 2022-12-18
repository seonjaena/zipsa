package com.project.zipsa.controller;

import com.project.zipsa.dto.GeneralResponseDto;
import com.project.zipsa.dto.building.BuildingResponseDto;
import com.project.zipsa.dto.building.SaveBuildingInfoRequestDto;
import com.project.zipsa.dto.enums.GENERAL_STATUS_ENUM;
import com.project.zipsa.dto.enums.GENERAL_SUCCESS_DETAIL;
import com.project.zipsa.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/building")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @GetMapping
    public GeneralResponseDto<GENERAL_STATUS_ENUM, Map<String, List<BuildingResponseDto>>> getBuildingInfos(Principal principal) {
        List<BuildingResponseDto> buildings = buildingService.getBuildingInfos(principal.getName());
        Map<String, List<BuildingResponseDto>> map = new HashMap<>();
        map.put("buildings", buildings);
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, map);
    }

    @PostMapping
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_SUCCESS_DETAIL> saveBuildingInfo(@RequestBody SaveBuildingInfoRequestDto saveBuildingInfoRequestDto, Principal principal) {
        buildingService.saveBuildingInfo(saveBuildingInfoRequestDto, principal.getName());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, GENERAL_SUCCESS_DETAIL.NULL);
    }

}
