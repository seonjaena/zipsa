package com.project.zipsa.repository;

import com.project.zipsa.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

    void deleteByRefreshTokenValue(String refreshTokenValue);

}
