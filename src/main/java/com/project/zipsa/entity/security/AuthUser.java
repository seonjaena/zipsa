package com.project.zipsa.entity.security;

import com.project.zipsa.entity.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
@Getter
public class AuthUser extends User {

    private final Users users;

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities
            , Users users) {
        super(username, password, authorities);
        this.users = users;
    }

}
