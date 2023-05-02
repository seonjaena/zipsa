package com.project.zipsa.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePwUnloginedRequestDto {

    private String userId;
    private String newPw1;
    private String newPw2;

}
