package com.project.zipsa.unit.service;

import com.project.zipsa.dto.auth.ResponseLoginDto;
import com.project.zipsa.dto.auth.TokenDto;
import com.project.zipsa.entity.Users;
import com.project.zipsa.entity.enums.USER_ROLE;
import com.project.zipsa.entity.enums.USER_STATUS;
import com.project.zipsa.repository.TokenRepository;
import com.project.zipsa.repository.UserRepository;
import com.project.zipsa.security.JwtProvider;
import com.project.zipsa.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("사용자 서비스 레이어 유닛 테스트")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenrepository;
    @Mock
    private Users users;
    @Mock
    private JwtProvider jwtProvider;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("로그인 성공")
    public void loginSuccess() throws IOException {

        // given
        String userId = "test-user-id";
        String userPw = "test-user-pw";
        String grantType = "Bearer ";
        Long accessTokenExpireDate = 1234567890L;
        given(users.getUserPw()).willReturn("test-user-pw");
        given(users.getUserRole()).willReturn(USER_ROLE.USER);
        given(userRepository.findByUserIdAndUserStatus(userId, USER_STATUS.NORMAL)).willReturn(Optional.of(users));
        given(passwordEncoder.matches(any(), any())).willReturn(userPw.equals(userPw));
        given(jwtProvider.createToken(users.getUserId(), List.of(users.getUserRole().getText()))).willReturn(new TokenDto(grantType, userId, userPw, accessTokenExpireDate));
        willDoNothing().given(tokenrepository).save(any());
        willDoNothing().given(tokenrepository).delete(any());

        // when
        ResponseLoginDto dto = userService.login(userId, userPw);

        // then
        assertThat(dto.getAccessToken()).isEqualTo(userId);
        assertThat(dto.getRefreshToken()).isEqualTo(userPw);

    }

}
