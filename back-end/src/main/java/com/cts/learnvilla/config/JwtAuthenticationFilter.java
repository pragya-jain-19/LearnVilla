package com.cts.learnvilla.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.learnvilla.service.impl.JwtService;
import com.cts.learnvilla.service.impl.StudentServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 
	private final JwtService jwtService;
	private final StudentServiceImpl userDetailsService;
//	public String loggedemailString;
 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
 
		String authHeader = request.getHeader("Authorization");
 
		System.out.println("jwt "+authHeader);
		if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.startsWith("Bearer null")) {
			log.info("Authorization header is missing or does not start with 'Bearer '");
			filterChain.doFilter(request, response);
			return;
		}
 
		String token = authHeader.substring(7);
		
		String username = jwtService.extractUsername(token);
		//loggedemailString=username;
 
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
 
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
 
			if (jwtService.isValid(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
 
				SecurityContextHolder.getContext().setAuthentication(authToken);
				log.info("Authentication successful for user: {}", username);
			} else {
				log.warn("Token validation failed for user: {}", username);
			}
 
		} else {
			log.info("Username is null or user is already authenticated");
		}
 
		filterChain.doFilter(request, response);
		log.info("Request processed successfully");
 
	}
 
}
