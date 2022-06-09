package com.project.zipsa.entity;

import lombok.*;
import javax.persistence.*;
@Entity
@Table(name = "REFRESH_TOKEN")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @Column(name = "REFRESH_TOKEN_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenIdx;

    @Column(name = "REFRESH_TOKEN_KEY")
    private String refreshTokenKey;

    @Column(name = "REFRESH_TOKEN_VALUE")
    private String refreshTokenValue;

    @Builder
    public RefreshToken(String refreshTokenKey, String refreshTokenValue) {
        this.refreshTokenKey = refreshTokenKey;
        this.refreshTokenValue = refreshTokenValue;
    }

}