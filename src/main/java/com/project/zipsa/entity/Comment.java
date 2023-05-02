package com.project.zipsa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "COMMENT")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @Column(name = "COMMENT_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_IDX", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_REPLY_IDX", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Comment commentReply;

    @Lob
    @Column(name = "COMMENT_CONTENT")
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_IDX", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Users writer;

}
