package com.project.zipsa.security;

import com.project.zipsa.dto.auth.TokenDto;
import com.project.zipsa.exception.custom.UnAuthenticatedException;
import com.project.zipsa.exception.custom.UnAuthorizedException;
import com.project.zipsa.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {

    private static final Set<String> NO_JWT_AUTH_PATH;

    static {
        NO_JWT_AUTH_PATH = new HashSet<>(Arrays.asList(
                "/api/healthcheck/alb",
                "/actuator"
        ));
    }

    @Value("${key.jwt.secret}")
    private String secretKey;
    @Value("${token.access-expire}")
    private Long accessTokenValidMillisecond;
    @Value("${token.refresh-expire}")
    private Long refreshTokenValidMillisecond;
    private final CustomUserDetailsService userDetailsService;
    private final String ROLES = "roles";
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_PREFIX = "Bearer ";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 생성
    public TokenDto createToken(String userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put(ROLES, roles);

        String accessToken = createAccessToken(claims);
        String refreshToken = createRefreshToken(claims);

        return TokenDto.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireDate(accessTokenValidMillisecond)
                .build();
    }

    // access token을 refresh함
    public String refreshAccessToken(String userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put(ROLES, roles);
        return createAccessToken(claims);
    }

    // JWT으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        if(claims.get(ROLES) == null) {
            throw new UnAuthenticatedException("접근 권한이 없습니다.");
        }
        String subject = claims.getSubject();
        UserDetails authUser = userDetailsService.loadUserByUsername(subject);
        return new UsernamePasswordAuthenticationToken(authUser, "", authUser.getAuthorities());
    }

    // HTTP Request의 Header에서 Token Parsing
    public String resolveToken(HttpServletRequest request) {

        if(NO_JWT_AUTH_PATH.contains(request.getRequestURI())) {
            return null;
        }

        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        log.info("[ AUTH ==> {} ]", authorization);

        if(StringUtils.hasText(authorization) && StringUtils.hasText(BEARER_PREFIX) && authorization.startsWith(BEARER_PREFIX)) {
            return authorization.substring(BEARER_PREFIX.length());
        }else {
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }

    }

    // JWT의 유효성 및 만료일자 확인
    public boolean validationToken(String token) {
        try {
            if(parseClaims(token).getExpiration().before(new Date())) {
                throw new JwtException("JWT Exception 토큰 만료");
            }
            return true;
        }catch(JwtException | IllegalArgumentException e) {
            log.error("message = {}", e.getMessage());
            return false;
        }
    }

    // access token 생성
    private String createAccessToken(Claims claims) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // refresh token 생성
    private String createRefreshToken(Claims claims) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
