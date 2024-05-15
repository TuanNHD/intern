package com.example.demo.web.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwtConfig.JwtTokenProvider;

@RestController("/api")
public class TokenController {
	@Autowired
	AuthenticationProvider authenticationProvider;
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@GetMapping("/genToken")
	public ResponseEntity<?> genToken(Principal principal) {
		 return ResponseEntity.ok("hello token");
	}
}
