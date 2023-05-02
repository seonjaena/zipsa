package com.project.zipsa.dto.room;

import com.project.zipsa.entity.enums.ROOM_CONTRACT_TYPE;
import com.project.zipsa.entity.enums.ROOM_STATUS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailInfoResponseDto {

    private String buildingName;
    private String buildingLocation;
    private Integer roomNumber;
    private Integer roomPrice;
    private Integer roomDeposit;
    private ROOM_CONTRACT_TYPE roomContractType;
    private ROOM_STATUS roomStatus;
    private String payDate;
    private LocalDate roomContractTerm;

}
