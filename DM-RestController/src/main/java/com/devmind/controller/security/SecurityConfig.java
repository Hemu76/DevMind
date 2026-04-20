package com.devmind.controller.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	private final UserConfig userDetailsService;

	public SecurityConfig(UserConfig userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		/*
		 * http.csrf(csrf -> csrf.disable()) .authorizeHttpRequests(auth ->
		 * auth.requestMatchers("/signup", "/signup.html", "/LoginPage.html")
		 * .permitAll().anyRequest().authenticated())
		 * .userDetailsService(userDetailsService) .formLogin(form ->
		 * form.loginPage("/LoginPage.html").defaultSuccessUrl("/home", true)
		 * .loginProcessingUrl("/login").failureUrl("/LoginPage.html?error=true").
		 * permitAll());
		 */

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/send","/usrLogin","/logout", "/home.html","/refresh","/signup", "/signup.html", "/LoginPage.html")
						.permitAll().requestMatchers("/testPostman").hasRole("ADMIN").anyRequest().authenticated())
				.userDetailsService(userDetailsService) //
				.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
				.httpBasic(httpBasic -> httpBasic.disable()).formLogin(form -> form.disable());

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
