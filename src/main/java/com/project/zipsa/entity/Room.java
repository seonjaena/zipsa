package com.project.zipsa.entity;

import com.project.zipsa.entity.Users;
import com.project.zipsa.entity.common.AuditUser;
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
    @Column(name = "ROOD_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomIdx;

    @Column(name = "ROOM_NUMBER")
    private Integer roomNumber;

    @Column(name = "ROOM_CONTRACT_TYPE", columnDefinition = "TINYINT(1)")
    private Short roomContractType;

    @Column(name = "ROOM_PRICE")
    private Integer roomPrice;

    @Column(name = "ROOM_DEPOSIT")
    private Integer roomDeposit;

    @Column(name = "ROOM_TYPE")
    private Short roomType;

    @Column(name = "IS_EMPTY")
    private Boolean isEmpty;

    @Column(name = "PAY_DATE")
    private String payDate;

    @Column(name = "ROOM_CONTRACT_TERM")
    private LocalDate roomContractTerm;

    @Column(name = "BUILDING_NAME")
    private String buildingName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_MASTER")
    private Users roomMaster;

}
