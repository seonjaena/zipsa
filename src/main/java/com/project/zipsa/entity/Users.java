package com.project.zipsa.entity;

import com.project.zipsa.dto.user.JoinRequestDto;
import com.project.zipsa.entity.common.AuditDateTime;
import com.project.zipsa.entity.enums.USER_ROLE;
import com.project.zipsa.entity.enums.USER_STATUS;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends AuditDateTime {

    @Id
    @Column(name = "USER_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PW")
    private String userPw;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_NICKNAME")
    private String userNickname;

    @Column(name = "USER_ROLE")
    @Convert(converter = USER_ROLE.Converter.class)
    private USER_ROLE userRole;

    @Column(name = "USER_STATUS")
    @Convert(converter = USER_STATUS.Converter.class)
    private USER_STATUS userStatus;

    @Column(name = "USER_PROFILE_IMAGE")
    private String userProfileImage;

    @Column(name = "USER_BIRTH")
    private LocalDate userBirth;

    @Column(name = "IS_ALERT")
    private Boolean isAlert;

    @Column(name = "IS_CHANGE_PW_REQUIRED")
    private Boolean isChangePwRequired;

    @Column(name = "USER_PHONE")
    private String userPhone;

    @Column(name = "USER_TOKEN")
    private String userToken;

    @Column(name = "USER_TOKEN_DATETIME")
    private LocalDateTime userTokenDatetime;

    public Users(JoinRequestDto joinRequestDto) {
        this.userId = joinRequestDto.getUserId();
        this.userPw = joinRequestDto.getUserPw();
        this.userName = joinRequestDto.getUserName();
        this.userNickname = joinRequestDto.getUserNickname();
        this.userBirth = joinRequestDto.getUserBirth();
        this.isAlert = joinRequestDto.getIsAlert();
        this.userPhone = joinRequestDto.getUserPhone();
        this.isChangePwRequired = false;
        this.userRole = USER_ROLE.USER;
        this.userStatus = USER_STATUS.NORMAL;
        this.userToken = joinRequestDto.getUserToken();
        this.userTokenDatetime = LocalDateTime.now();
    }

    public static Users from(JoinRequestDto joinRequestDto) {
        return new Users(joinRequestDto);
    }

    public void changeAlert(boolean isAlert) {
        this.isAlert = isAlert;
    }

    public void changePw(String userPw) {
        this.userPw = userPw;
    }

    public void deleteMyInfo() {
        this.userStatus = USER_STATUS.DELETED;
    }

    public void changeNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void changeUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void changeUserBirth(LocalDate userBirth) {
        this.userBirth = userBirth;
    }

    public void changeUserId(String userId) {
        this.userId = userId;
    }

    public void changeUserProfileImage(String profileImage) {
        this.userProfileImage = profileImage;
    }

}
