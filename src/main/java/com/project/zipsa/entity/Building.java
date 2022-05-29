package com.project.zipsa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BUILDING")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Building {

    @Id
    @Column(name = "BUILDING_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buildingIdx;

    @Column(name = "BUILDING_NAME")
    private String buildingName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUILDING_MASTER_IDX")
    private Users buildingMaster;

}
