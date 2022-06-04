package com.project.zipsa.controller;

import com.project.zipsa.dto.GeneralResponseDto;
import com.project.zipsa.dto.auth.LoginRequestDto;
import com.project.zipsa.dto.auth.ResponseLoginDto;
import com.project.zipsa.dto.enums.GENERAL_STATUS_ENUM;
import com.project.zipsa.dto.enums.GENERAL_SUCCESS_DETAIL;
import com.project.zipsa.dto.enums.PHONE_CHECK_TYPE;
import com.project.zipsa.dto.user.JoinPageResponseDto;
import com.project.zipsa.dto.user.JoinRequestDto;
import com.project.zipsa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

}
