package com.project.zipsa.entity;

import lombok.*;

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
    @JoinColumn(name = "BUILDING_MASTER_IDX", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Users buildingMaster;

    @Column(name = "BUILDING_LOCATION")
    private String buildingLocation;

    public static Building of(String buildingName, Users buildingMaster, String buildingLocation) {
        Building building = new Building();
        building.setBuildingName(buildingName);
        building.setBuildingMaster(buildingMaster);
        building.setBuildingLocation(buildingLocation);
        return building;
    }

}
