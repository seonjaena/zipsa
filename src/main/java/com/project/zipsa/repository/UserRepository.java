package com.project.zipsa.repository;

import com.project.zipsa.entity.Users;
import com.project.zipsa.entity.enums.USER_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserIdAndUserStatus(String userId, USER_STATUS userStatus);
    Optional<Users> findByUserNameAndUserStatusAndUserBirthAndUserPhone(String userName, USER_STATUS userStatus, LocalDate userBirth, String userPhone);
    Optional<Users> findByUserNameAndUserStatusAndUserBirthAndUserPhoneAndUserId(String userName, USER_STATUS userStatus, LocalDate userBirth, String userPhone, String userId);
    boolean existsByUserId(String userId);
    boolean existsByUserNickname(String userNickname);
    boolean existsByUserPhone(String userPhone);

}
