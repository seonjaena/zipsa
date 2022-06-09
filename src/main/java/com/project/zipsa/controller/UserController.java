package com.project.zipsa.controller;

import com.project.zipsa.dto.GeneralResponseDto;
import com.project.zipsa.dto.auth.LoginRequestDto;
import com.project.zipsa.dto.auth.ResponseLoginDto;
import com.project.zipsa.dto.enums.GENERAL_STATUS_ENUM;
import com.project.zipsa.dto.enums.GENERAL_SUCCESS_DETAIL;
import com.project.zipsa.dto.enums.PHONE_CHECK_TYPE;
import com.project.zipsa.dto.user.*;
import com.project.zipsa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, ResponseLoginDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, userService.login(loginRequestDto.getUserId(), loginRequestDto.getUserPw()));
    }

    @GetMapping(value = "/join")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, List<JoinPageResponseDto>> getUserInfo() {
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, userService.joinPage());
    }

    @PostMapping(value = "/join")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_SUCCESS_DETAIL> join(@RequestBody JoinRequestDto joinRequestDto) {
        userService.join(joinRequestDto);
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, GENERAL_SUCCESS_DETAIL.NULL);
    }

    @GetMapping(value = "/phone")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_SUCCESS_DETAIL> checkPhone(@RequestParam(name = "userPhone") String userPhone,
                                                                                      @RequestParam(name = "type") PHONE_CHECK_TYPE type) {
        userService.checkPhone(userPhone, type);
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, GENERAL_SUCCESS_DETAIL.NULL);
    }

    @PostMapping(value = "/phone")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_SUCCESS_DETAIL> checkPhoneCode(@RequestParam(name = "userPhone") String userPhone,
                                                                                          @RequestParam(name = "code") String code) {
        userService.checkPhoneCode(userPhone, code);
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, GENERAL_SUCCESS_DETAIL.NULL);
    }

    @PostMapping(value = "/find/id")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> findId(@RequestBody FindIdRequestDto findIdRequestDto) {
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, userService.findId(findIdRequestDto));
    }

    @PostMapping(value = "/find/pw")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_SUCCESS_DETAIL> findPw(@RequestBody FindPwRequestDto findPwRequestDto) {
        userService.findPw(findPwRequestDto);
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, GENERAL_SUCCESS_DETAIL.NULL);
    }

    @PatchMapping(value = "/pw")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_SUCCESS_DETAIL> changePw(@RequestBody ChangePwRequestDto changePwRequestDto) {
        userService.changePw(changePwRequestDto);
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, GENERAL_SUCCESS_DETAIL.NULL);
    }

    @GetMapping
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GetMeResponseDto> getMyInfo(Principal principal) {
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, userService.getMyInfo(principal.getName()));
    }

    @PatchMapping(value = "/alert")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_SUCCESS_DETAIL> changeAlert(@RequestBody ChangeAlertRequestDto changeAlertRequestDto,
                                                                                       Principal principal) {
        userService.changeAlert(changeAlertRequestDto, principal.getName());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, GENERAL_SUCCESS_DETAIL.NULL);
    }

    @DeleteMapping
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_SUCCESS_DETAIL> deleteMyInfo(@RequestBody DeleteMeRequestDto deleteMeRequestDto,
                                                                                        Principal principal) {
        userService.deleteMyInfo(deleteMeRequestDto, principal.getName());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, GENERAL_SUCCESS_DETAIL.NULL);
    }

    @PatchMapping(value = "/nickname")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, ChangeNicknameResponseDto> changeNickname(@RequestParam(name = "userNickname") String userNickname, Principal principal) {
        String changedNickname = userService.changeUserNickname(userNickname, principal.getName());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, new ChangeNicknameResponseDto(changedNickname));
    }

    @PatchMapping(value = "/phone")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, ChangePhoneResponseDto> changePhone(@RequestParam(name = "userPhone") String userPhone, Principal principal) {
        String changedPhone = userService.changeUserPhone(userPhone, principal.getName());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, new ChangePhoneResponseDto(changedPhone));
    }

    @PatchMapping(value = "/birth")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, ChangeBirthResponseDto> changeBirth(@RequestParam(name = "userBirth")LocalDate userBirth,
                                                                       Principal principal) {
        LocalDate changedBirth = userService.changeUserBirth(userBirth, principal.getName());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, new ChangeBirthResponseDto(changedBirth));
    }

    @PatchMapping(value = "/profile")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, ChangeProfileImageRequestDto> changeProfileImage(@RequestParam(name = "userProfileImage") MultipartFile userProfileImage,
                                                                                                    Principal principal) throws IOException {
        String savedFileName = userService.changeUserProfileImage(userProfileImage, principal.getName());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, new ChangeProfileImageRequestDto(savedFileName));
    }

    @PatchMapping(value = "/id")
    public GeneralResponseDto<GENERAL_STATUS_ENUM, ChangeIdResponseDto> changeUserId(@RequestParam(name = "userId") String userId,
                                                                        Principal principal) {
        String changedUserId = userService.changeUserId(userId, principal.getName());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.SUCCESS, new ChangeIdResponseDto(changedUserId));
    }

}
