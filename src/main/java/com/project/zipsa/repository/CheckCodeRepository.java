package com.project.zipsa.repository;

import com.project.zipsa.entity.CheckCode;
import com.project.zipsa.entity.enums.CHECK_CODE_TYPE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckCodeRepository extends JpaRepository<CheckCode, Long> {
    Optional<CheckCode> findFirstByCheckCodeTypeAndDeviceOrderByCheckCodeIdxDesc(CHECK_CODE_TYPE checkCodeType, String device);

}
