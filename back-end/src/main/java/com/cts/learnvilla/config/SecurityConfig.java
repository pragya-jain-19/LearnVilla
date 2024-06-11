package com.cts.learnvilla.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.cts.learnvilla.service.impl.StudentServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	@Autowired
	private final StudentServiceImpl userDetailsServiceImpl;

	@Autowired
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private PasswordEncoder passwordEncod;

	private final String[] PERMIT_ALL = { "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
			"/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**", "/swagger-ui/**",
			// other public endpoints of your API may be appended to this array
			"/learnvilla/api/login", "/learnvilla/api/register", "/learnvilla/api/categories/view-all-categories",
			"/learnvilla/api/courses/view-all-courses", "/learnvilla/api/courses/view-course/course-id/{courseId}",
			"/learnvilla/api/courses/view-course/course-name/{courseName}",
			"/learnvilla/api/sections/view-all-sections/course-id/{courseId}",
			"/learnvilla/api/sections/view-all-sections/course-name/{courseName}",
			"/learnvilla/api/courses/view-all-courses/course-name-like/{name}",
			"/learnvilla/api/courses/view-all-courses/category-id/{categoryId}" };

	private final String[] ADMIN = { "/learnvilla/api/categories/add-category",
			"/learnvilla/api/categories/update-category", "/learnvilla/api/categories/delete-category/{categoryId}",
			"/learnvilla/api/courses/add-course", "/learnvilla/api/courses/update-course",
			"/learnvilla/api/courses/delete-course/{courseId}", "/learnvilla/api/enrollments/view-all-enrollments",
			"/learnvilla/api/enrollments/update-enrollment", "/learnvilla/api/enrollments/update-completion-status",
			"/learnvilla/api/enrollments/view-all-enrollments/course/{courseId}",
			"/learnvilla/api/payments/view-all-payments", "/learnvilla/api/payments/view-payment/{paymentId}",
			"/learnvilla/api/payments/update-payment-status/{paymentId}/{status}",
			"/learnvilla/api/sections/add-section", "/learnvilla/api/sections/view-all-sections",
			"/learnvilla/api/sections/update-section",
			"/learnvilla/api/sections/delete-section/course-id/{courseId}/{sectionNumber}",
			"/learnvilla/api/students/view-all-students", "/learnvilla/api/students/delete-student/{studentId}" };

	private final String[] STUDENT = { "/learnvilla/api/enrollments/add-enrollment",
			"/learnvilla/api/payments/add-payment", "/learnvilla/api/wishlist/insert-course/{studentId}/{courseId}",
			"/learnvilla/api/wishlist/delete-course/{studentId}/{courseId}",
			"/learnvilla/api/wishlist/view-wishlist/{studentId}",
			"/learnvilla/api/wishlist/delete-wishlist/{studentId}" };

	private final String[] ANY_AUTH = { "/learnvilla/api/enrollments/view-all-enrollments/student/{studentId}",
			"/learnvilla/api/enrollments/view-enrollment/{enrollmentId}",
			"/learnvilla/api/students/view-student/{studentId}", "/learnvilla/api/students/update-student",
			"/learnvilla/api/payments/view-all-payments/student-id/{studentId}",
			"/learnvilla/api/sections/view-section/{sectionId}" };

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable)

				.authorizeHttpRequests(req -> req.requestMatchers(PERMIT_ALL).permitAll().requestMatchers(ADMIN)
						.hasAuthority("ADMIN").requestMatchers(STUDENT).hasAuthority("STUDENT")
						.requestMatchers(ANY_AUTH).hasAnyAuthority("STUDENT", "ADMIN").anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration config = new CorsConfiguration();
						config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
						config.setAllowedMethods(Collections.singletonList("*"));
						config.setAllowCredentials(true);
						config.setAllowedHeaders(Collections.singletonList("*"));
						config.setExposedHeaders(Arrays.asList("Authorization"));
						config.setMaxAge(3600L);
						return config;
					}
				}))
//				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
		authenticationProvider.setPasswordEncoder(passwordEncod);
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenMange(AuthenticationConfiguration autheConfig) throws Exception {
		return autheConfig.getAuthenticationManager();
	}
}