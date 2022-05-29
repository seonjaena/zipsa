package com.project.zipsa.dto.auth;

import com.project.zipsa.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseLoginDto {

    private String userId;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;

    @Builder
    public ResponseLoginDto(Users users, List<String> roles, String accessToken, String refreshToken) {
        this.userId = users.getUserId();
        this.roles = roles;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
