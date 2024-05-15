package com.example.demo.securityConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.authenticationProvider.CustomAuthenProvider;
import com.example.demo.jwtConfig.JwtFilter;
import com.example.demo.jwtConfig.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfiguer {
	@Autowired
	CustomAuthenProvider authenProvider;
	@Autowired
	JwtFilter jwtFilter;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	AuthenticationSuccessHandler authenticationSuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf(cs -> cs.disable())
	        .authorizeHttpRequests(req -> req
	            .requestMatchers("/car/hello").permitAll()
	            .requestMatchers("/login").permitAll()
	            .requestMatchers("/h2-console/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .formLogin(lg -> lg
	            .loginPage("/car/hello")
	            .loginProcessingUrl("/api/car")
	            .successHandler(authenticationSuccessHandler)
	            .failureUrl("/car/hello?error=true")
	        )
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	        .build();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenProvider);
	}
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
