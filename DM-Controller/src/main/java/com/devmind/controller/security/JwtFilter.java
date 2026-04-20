package com.devmind.controller.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();

		boolean isPublic = SecurityConstants.PUBLIC_URLS.stream().anyMatch(path::startsWith);

		if (isPublic) {
			filterChain.doFilter(request, response);
			return;
		}

		String header = request.getHeader("Authorization");

		// ❌ No token
		if (header == null || !header.startsWith("Bearer ")) {
			sendError(response, "Missing or invalid Authorization header");
			return;
		}

		String token = header.substring(7);

		try {
			String username = JwtUtil.extractUsername(token);
			String role = JwtUtil.extractRole(token);

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
					List.of(() -> role));

			SecurityContextHolder.getContext().setAuthentication(auth);

		} catch (Exception e) {
			sendError(response, "Invalid or expired token");
			return;
		}

		filterChain.doFilter(request, response);
	}

	private void sendError(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");

		response.getWriter().write("{ \"error\": \"" + message + "\" }");
	}
}