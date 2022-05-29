package com.project.zipsa.service;

import com.project.zipsa.dto.auth.ResponseLoginDto;
import com.project.zipsa.dto.auth.TokenDto;
import com.project.zipsa.entity.RefreshToken;
import com.project.zipsa.entity.Users;
import com.project.zipsa.entity.enums.USER_ROLE;
import com.project.zipsa.entity.enums.USER_STATUS;
import com.project.zipsa.exception.custom.UnAuthenticatedException;
import com.project.zipsa.exception.custom.UnAuthorizedException;
import com.project.zipsa.repository.TokenRepository;
import com.project.zipsa.repository.UserRepository;
import com.project.zipsa.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public ResponseLoginDto login(String userId, String userPw) {
        Users user = userRepository.findByUserIdAndUserStatus(userId, USER_STATUS.NORMAL)
                .orElseThrow(() -> new UnAuthenticatedException("잘못된 아이디 혹은 비밀번호입니다."));

        if(!passwordEncoder.matches(userPw, user.getUserPw())) {
            throw new UnAuthenticatedException("잘못된 아이디 혹은 비밀번호입니다.");
        }

        if (!(user.getUserRole() == USER_ROLE.USER || user.getUserRole() == USER_ROLE.ADMIN)) {
            throw new UnAuthorizedException("권한이 없는 사용자입니다.");
        }

        TokenDto tokenDto = jwtProvider.createToken(user.getUserId(), List.of(user.getUserRole().getText()));

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshTokenKey(user)
                .refreshTokenValue(tokenDto.getRefreshToken())
                .build();

        tokenRepository.saveAndFlush(refreshToken);
        return new ResponseLoginDto(user, List.of(user.getUserRole().getText()), tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

}
