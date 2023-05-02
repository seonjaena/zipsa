package com.project.zipsa.entity;

import com.project.zipsa.dto.user.JoinRequestDto;
import com.project.zipsa.entity.common.AuditDateTime;
import com.project.zipsa.entity.enums.USER_ROLE;
import com.project.zipsa.entity.enums.USER_STATUS;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(builderMethodName = "innerBuild")
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

    public static UsersBuilder builder(String userId, String userPw, String userName,
                                       String userNickname, LocalDate userBirth, Boolean isAlert, String userPhone) {
        return innerBuild()
                .userId(userId)
                .userPw(userPw)
                .userName(userName)
                .userNickname(userNickname)
                .userBirth(userBirth)
                .isAlert(isAlert)
                .userPhone(userPhone)
                .isChangePwRequired(false)
                .userRole(USER_ROLE.USER)
                .userStatus(USER_STATUS.NORMAL);
    }

    public static Users from(JoinRequestDto joinRequestDto) {
        Users users = new Users();
        users.userId = joinRequestDto.getUserId();
        users.userPw = joinRequestDto.getUserPw();
        users.userName = joinRequestDto.getUserName();
        users.userNickname = joinRequestDto.getUserNickname();
        users.userBirth = joinRequestDto.getUserBirth();
        users.isAlert = joinRequestDto.getIsAlert();
        users.userPhone = joinRequestDto.getUserPhone();
        users.isChangePwRequired = false;
        users.userRole = USER_ROLE.USER;
        users.userStatus = USER_STATUS.NORMAL;
        users.userToken = joinRequestDto.getUserToken();
        users.userTokenDatetime = LocalDateTime.now();
        return users;
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
