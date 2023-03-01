package com.project.zipsa.dto.auth;

import com.project.zipsa.validate.RegExValidate;
import com.project.zipsa.validate.VALIDATE_REGEX;
import com.project.zipsa.validate.ValidationGroup.RegExValidateGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @RegExValidate(
            regex = VALIDATE_REGEX.USER_ID,
            message = "아이디를 이메일 형식으로 작성하세요.",
            groups = RegExValidateGroup.class
    )
    private String userId;

    @RegExValidate(
            regex = VALIDATE_REGEX.PASSWORD,
            message = "비밀번호에 영문(대문자 혹은 소문자), 숫자, 특수 문자(!@#$%&^()*+-_+=~`)를 최소 한 글자 이상 포함하고 8 ~ 16 자리 사이여야 합니다.",
            groups = RegExValidateGroup.class
    )
    private String userPw;

}
