package com.project.zipsa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "VOTE")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

    @Id
    @Column(name = "VOTE_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_IDX")
    private Board board;

    @Column(name = "VOTE_CONTENT")
    private String voteContent;

    @Column(name = "VOTE_COUNT")
    private Integer voteCount;

}
