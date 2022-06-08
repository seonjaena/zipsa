package com.project.zipsa.dto.user;

import com.project.zipsa.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMeResponseDto {

    private String userProfileImage;
    private String userId;
    private String userName;
    private String userNickname;
    private LocalDate userBirth;
    private Boolean isAlert;
    private Boolean isMarketingSMS;
    private Boolean isMarketingEmail;
    private String userPhone;

    public GetMeResponseDto(Users user) {
        this.userProfileImage = user.getUserProfileImage();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userNickname = user.getUserNickname();
        this.userBirth = user.getUserBirth();
        this.isAlert = user.getIsAlert();
        this.isMarketingSMS = user.getIsMarketingSMS();
        this.isMarketingEmail = user.getIsMarketingEmail();
        this.userPhone = user.getUserPhone();
    }

}
