package com.project.zipsa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.zipsa.dto.GeneralResponseDto;
import com.project.zipsa.dto.enums.GENERAL_STATUS_ENUM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String result = objectMapper.writeValueAsString(new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, messageSource.getMessage("error.jwt.access.expire", null, Locale.KOREA)));

        response.getWriter().write(result);
    }

}
