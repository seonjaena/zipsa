package com.project.zipsa.entity;

import com.project.zipsa.entity.common.AuditUser;
import com.project.zipsa.entity.enums.BOARD_TYPE;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BOARDS")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends AuditUser {

    @Id
    @Column(name = "BOARDS_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUILDING_IDX", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Building building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_IDX", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Users writer;

    @Column(name = "BOARDS_TYPE")
    @Convert(converter = BOARD_TYPE.Converter.class)
    private BOARD_TYPE boardType;

    @Column(name = "BOARDS_TITLE")
    private String boardTitle;

    @Lob
    @Column(name = "BOARDS_CONTENT")
    private String boardContent;

    @Column(name = "PERMIT_COMMENT")
    private Boolean permitComment;

    @Column(name = "PERMIT_LIKE")
    private Boolean permitLike;

    @Column(name = "LIKES")
    private Integer like;

    @Column(name = "DISLIKES")
    private Integer disLike;

}
