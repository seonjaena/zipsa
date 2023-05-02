package com.project.zipsa.controller;

import com.project.zipsa.dto.GeneralResponseDto;
import com.project.zipsa.dto.enums.GENERAL_STATUS_ENUM;
import com.project.zipsa.dto.room.RoomDetailInfoResponseDto;
import com.project.zipsa.dto.room.RoomInfoResponseDto;
import com.project.zipsa.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public GeneralResponseDto<GENERAL_STATUS_ENUM, Map<String, List<RoomInfoResponseDto>>> getRoomInfos(Principal principal) {
        List<RoomInfoResponseDto> roomInfos = roomService.getRoomInfos(principal.getName());
        Map<String, List<RoomInfoResponseDto>> map = new HashMap<>();
        map.put("rooms", roomInfos);
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, map);
    }

    @GetMapping(value = "/{roomIdx}")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, RoomDetailInfoResponseDto> getRoomInfo(@PathVariable(name = "roomIdx") Long roomIdx) {
        RoomDetailInfoResponseDto responseDto = roomService.getRoomInfo(roomIdx);
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, responseDto);
    }

}
