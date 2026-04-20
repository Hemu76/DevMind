package com.devmind.controller.security;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.devmind.model.users.UserEntity;
import com.devmind.service.repository.UserRepository;

@Service
public class UserConfig implements UserDetailsService {

	private final UserRepository userRepository;

	public UserConfig(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {

		UserEntity user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				List.of(() -> user.getRole()));
	}
	
	//Used this previously
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails user = User.builder()
                .username("admin")
                .password(encoder.encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}