package com.project.zipsa.integration;

import com.project.zipsa.dto.auth.TokenDto;
import com.project.zipsa.entity.RefreshToken;
import com.project.zipsa.entity.Users;
import com.project.zipsa.entity.enums.USER_ROLE;
import com.project.zipsa.entity.enums.USER_STATUS;
import com.project.zipsa.repository.CheckCodeRepository;
import com.project.zipsa.repository.TokenRepository;
import com.project.zipsa.repository.UserRepository;
import com.project.zipsa.security.JwtProvider;
import com.project.zipsa.service.UserService;
import com.project.zipsa.util.FileUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class Study {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CheckCodeRepository checkCodeRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private FileUtil fileUtil;
    @Mock
    private MessageSource messageSource;
    @Mock
    private Users user;
    @InjectMocks
    private UserService userService;

    @Test
    void testVerifyTime() {
        TokenDto tokenDto = new TokenDto();
        given(user.getUserId()).willReturn("test-user");
        given(user.getUserRole()).willReturn(USER_ROLE.USER);
        given(userRepository.findByUserIdAndUserStatus(any(), any())).willReturn(Optional.ofNullable(user));
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        given(jwtProvider.createToken(any(), any())).willReturn(tokenDto);
        willDoNothing().given(tokenRepository).delete(any());
        willDoNothing().given(tokenRepository).save(any());
        userService.login("test-user", "test-user-pwd");
        then(passwordEncoder).should(times(1)).matches(any(), any());
    }

}
