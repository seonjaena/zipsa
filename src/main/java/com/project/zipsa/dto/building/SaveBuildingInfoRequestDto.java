package com.project.zipsa.dto.building;

import com.project.zipsa.entity.enums.ROOM_CONTRACT_TYPE;
import com.project.zipsa.entity.enums.ROOM_TYPE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveBuildingInfoRequestDto {

    private String buildingName;
    private String buildingLocation;
    private Integer roomNumber;
    private ROOM_CONTRACT_TYPE roomContractType;
    private Integer roomPrice;
    private Integer roomDeposit;
    private ROOM_TYPE roomType;

}
