package com.project.zipsa.entity;

import com.project.zipsa.entity.common.AuditUser;
import com.project.zipsa.entity.enums.ROOM_CONTRACT_TYPE;
import com.project.zipsa.entity.enums.ROOM_STATUS;
import com.project.zipsa.entity.enums.ROOM_TYPE;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ROOM")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends AuditUser {

    @Id
    @Column(name = "ROOM_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomIdx;

    @Column(name = "ROOM_NUMBER")
    private Integer roomNumber;

    @Column(name = "ROOM_CONTRACT_TYPE")
    @Convert(converter = ROOM_CONTRACT_TYPE.Converter.class)
    private ROOM_CONTRACT_TYPE roomContractType;

    @Column(name = "ROOM_PRICE")
    private Integer roomPrice;

    @Column(name = "ROOM_DEPOSIT")
    private Integer roomDeposit;

    @Column(name = "ROOM_TYPE")
    @Convert(converter = ROOM_TYPE.Converter.class)
    private ROOM_TYPE roomType;

    @Column(name = "ROOM_STATUS")
    @Convert(converter = ROOM_STATUS.Converter.class)
    private ROOM_STATUS roomStatus;

    @Column(name = "PAY_DATE")
    private String payDate;

    @Column(name = "ROOM_CONTRACT_TERM")
    private LocalDate roomContractTerm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUILDING_IDX")
    private Building building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TENANT_IDX")
    private Users tenant;

    public static Room of(Integer roomNumber, ROOM_CONTRACT_TYPE roomContractType, Integer roomPrice, Integer roomDeposit, ROOM_TYPE roomType, Building building) {
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomContractType(roomContractType);
        room.setRoomPrice(roomPrice);
        room.setRoomDeposit(roomDeposit);
        room.setRoomType(roomType);
        room.setRoomStatus(ROOM_STATUS.EMPTY);
        room.setBuilding(building);
        return room;
    }

}
