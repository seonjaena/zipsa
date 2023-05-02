package com.project.zipsa.entity;

import com.project.zipsa.entity.enums.BOARD_LIKE_TYPE;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BOARDS_LIKE")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLike {

    @Id
    @Column(name = "BOARDS_LIKE_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardLikeIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARDS_IDX", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_IDX", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Users user;

    @Column(name = "BOARDS_LIKE_TYPE")
    @Convert(converter = BOARD_LIKE_TYPE.Converter.class)
    private BOARD_LIKE_TYPE boardLikeType;

}
