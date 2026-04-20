package com.devmind.controller.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devmind.controller.repository.RefreshTokenService;
import com.devmind.controller.security.JwtUtil;
import com.devmind.model.users.RefreshToken;
import com.devmind.model.users.SignupRequest;
import com.devmind.model.users.UserEntity;
import com.devmind.service.repository.UserRepository;

@RestController
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RefreshTokenService refreshTokenService;

	@GetMapping("/testPostman")
	public String testPostman() {
		return "Cool Working!!"; // loads login.html
	}

	@GetMapping("/home")
	public String home() {
		return "Cool Working!!"; // loads login.html
	}

	@PostMapping("/usrLogin")
	public Map<String, String> login(@RequestBody SignupRequest request) {

		UserEntity user = userRepository.findByUsername(request.getUsername());

		if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		String accessToken = JwtUtil.generateToken(user.getUsername(), user.getRole());

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

		Map<String, String> response = new HashMap<>();
		response.put("accessToken", accessToken);
		response.put("refreshToken", refreshToken.getToken());

		return response;
	}

	@PostMapping("/signup")
	public String signup(@RequestBody SignupRequest request) {

		if (userRepository.findByUsername(request.getUsername()) != null) {
			return "User already exists";
		}

		UserEntity user = new UserEntity();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword())); // 🔥 encode
		user.setRole("ROLE_USER");

		userRepository.save(user);

		return "User registered successfully";
	}

	@PostMapping("/refresh")
	public String refresh(@RequestBody Map<String, String> request) {

		String refreshToken = request.get("refreshToken");

		RefreshToken token = refreshTokenService.verifyToken(refreshToken);

		String username = token.getUser().getUsername();
		String role = token.getUser().getRole();

		return JwtUtil.generateToken(username, role);
	}

	@PostMapping("/logout")
	public String logout(@RequestBody Map<String, String> request) {

		String username = request.get("username");

		UserEntity user = userRepository.findByUsername(username);

		refreshTokenService.deleteByUser(user.getId());

		return "Logged out successfully";
	}
}
