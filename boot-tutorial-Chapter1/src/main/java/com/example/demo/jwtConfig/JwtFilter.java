package com.example.demo.jwtConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.securityConfig.CustomAuthenticationSuccessHandler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	CustomAuthenticationSuccessHandler authenticationSuccessHandler;

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Lấy JWT từ header Authorization
        String token = request.getHeader("Authorization");

        // Xác thực token
        if (token != null && token.startsWith("Bearer ")) {
            // Lấy thông tin xác thực từ token
        	String tokenAuthen = token.substring(7);
//            Authentication authentication = jwtTokenProvider.getAuthentication(tokenAuthen);
        	Authentication authentication = authenticationSuccessHandler.returnCheck();
            // Đặt thông tin xác thực vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }

        // Tiếp tục xử lý yêu cầu
        else {
        SecurityContextHolder.clearContext();
        filterChain.doFilter(request, response);
        }
    }
}
