package com.project.zipsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class ZipsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipsaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication || !authentication.isAuthenticated()) {
			return null;
		}
		User user = (User) authentication.getPrincipal();
		return () -> Optional.of(user.getUsername());
	}

}
