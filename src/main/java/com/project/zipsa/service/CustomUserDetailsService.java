package com.project.zipsa.service;

import com.project.zipsa.entity.Users;
import com.project.zipsa.entity.security.AuthUser;
import com.project.zipsa.exception.custom.UnAuthenticatedException;
import com.project.zipsa.exception.custom.UserNotFoundException;
import com.project.zipsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Users users = userRepository.findByUserNameAndIsDeletedFalse(userId).orElseThrow(() -> new UsernameNotFoundException("계정을 찾을 수 없습니다."));

        Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(users.getUserRole().getText()));

        return new AuthUser(users.getUserId(), users.getUserPw(), roles, users);
    }

}
