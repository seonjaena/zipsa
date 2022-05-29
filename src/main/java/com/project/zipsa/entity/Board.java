package com.project.zipsa.entity;

import com.project.zipsa.entity.common.AuditUser;
import com.project.zipsa.entity.enums.BOARD_TYPE;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BOARD")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends AuditUser {

    @Id
    @Column(name = "BOARD_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUILDING_IDX")
    private Building building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_IDX")
    private Users writer;

    @Column(name = "BOARD_TYPE")
    @Convert(converter = BOARD_TYPE.Converter.class)
    private BOARD_TYPE boardType;

    @Column(name = "BOARD_TITLE")
    private String boardTitle;

    @Column(name = "BOARD_CONTENT", columnDefinition = "MEDIUMTEXT")
    private String boardContent;

    @Column(name = "PERMIT_COMMENT")
    private Boolean permitComment;

    @Column(name = "PERMIT_LIKE")
    private Boolean permitLike;

    @Column(name = "LIKE")
    private Integer like;

    @Column(name = "DISLIKE")
    private Integer disLike;

}
