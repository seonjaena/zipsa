package com.project.zipsa.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestDto {

    private String userId;
    private String userPw;
    private String userName;
    private String userNickname;
    private LocalDate userBirth;
    private Boolean isAlert;
    private String userPhone;
    private String userToken;

}
