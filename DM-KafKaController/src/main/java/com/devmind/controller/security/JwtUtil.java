package com.devmind.controller.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

	private static final String SECRET = "mysecretkeymysecretkeymysecretkey123";
	private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

	public static String generateToken(String username, String role) {
		return Jwts.builder().setSubject(username).claim("role", role).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*15)).signWith(key).compact();
	}

	public static String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	public static String extractRole(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role",
				String.class);
	}
}