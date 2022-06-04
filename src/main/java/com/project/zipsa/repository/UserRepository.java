package com.project.zipsa.repository;

import com.project.zipsa.entity.Users;
import com.project.zipsa.entity.enums.USER_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserIdAndUserStatus(String username, USER_STATUS userStatus);
    Optional<Users> findByUserPhone(String userPhone);

}
