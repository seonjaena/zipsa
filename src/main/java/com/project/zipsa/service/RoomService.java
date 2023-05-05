package com.project.zipsa.service;

import com.project.zipsa.dto.room.RoomDetailInfoResponseDto;
import com.project.zipsa.dto.room.RoomInfoResponseDto;
import com.project.zipsa.entity.Room;
import com.project.zipsa.entity.Users;
import com.project.zipsa.exception.custom.NoSuchRoomException;
import com.project.zipsa.repository.RoomRepository;
import com.project.zipsa.repository.dsl.RoomDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final UserService userService;
    private final RoomRepository roomRepository;
    private final RoomDslRepository roomDslRepository;
    private final MessageSource messageSource;

    public List<RoomInfoResponseDto> getRoomInfos(String userId) {
        Users user = userService.getUserNotDeleted(userId);
        List<Room> rentList = roomDslRepository.findAllByTenant(user);
        List<Room> ownList = roomDslRepository.findAllByBuildingMaster(userId);

        List<RoomInfoResponseDto> rentDtoList = rentList.stream().map(r ->
                new RoomInfoResponseDto(r.getRoomIdx(), false, r.getBuilding().getBuildingName(), r.getRoomNumber(),
                        r.getRoomPrice(), r.getRoomDeposit(), r.getRoomContractType(), r.getRoomStatus(), r.getRoomContractTerm())
        ).collect(Collectors.toList());
        List<RoomInfoResponseDto> ownDtoList = ownList.stream().map(o ->
                new RoomInfoResponseDto(o.getRoomIdx(), true, o.getBuilding().getBuildingName(), o.getRoomNumber(),
                        o.getRoomPrice(), o.getRoomDeposit(), o.getRoomContractType(), o.getRoomStatus(), o.getRoomContractTerm())
        ).collect(Collectors.toList());

        List<RoomInfoResponseDto> responseDtoList = new ArrayList<>();
        responseDtoList.addAll(rentDtoList);
        responseDtoList.addAll(ownDtoList);
        return responseDtoList;
    }

    public RoomDetailInfoResponseDto getRoomInfo(Long roomIdx) {
        Room room = roomRepository.findByRoomIdx(roomIdx)
                .orElseThrow(() -> new NoSuchRoomException(messageSource.getMessage("error.room.info", null, Locale.KOREA)));
        return new RoomDetailInfoResponseDto(room.getBuilding().getBuildingName(), room.getBuilding().getBuildingLocation(),
                room.getRoomNumber(), room.getRoomPrice(), room.getRoomDeposit(), room.getRoomContractType(),
                room.getRoomStatus(), room.getPayDate(), room.getRoomContractTerm());
    }

}
