package com.devmind.controller.repository;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.devmind.model.users.RefreshToken;
import com.devmind.model.users.UserEntity;
import com.devmind.service.repository.RefreshTokenRepository;
import com.devmind.service.repository.UserRepository;

@Service
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userRepository = userRepository;
	}

	public RefreshToken createRefreshToken(String username) {

		UserEntity user = userRepository.findByUsername(username);

		RefreshToken token = new RefreshToken();
		token.setUser(user);
		token.setToken(UUID.randomUUID().toString());
		token.setExpiryDate(Instant.now().plusSeconds(60*1000*24));

		return refreshTokenRepository.save(token);
	}

	public RefreshToken verifyToken(String token) {

		RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token not found"));

		if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
			refreshTokenRepository.delete(refreshToken);
			throw new RuntimeException("Token expired");
		}

		return refreshToken;
	}

	public void deleteByUser(Long userId) {
		refreshTokenRepository.deleteByUser_Id(userId);
	}
}