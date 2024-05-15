package com.example.demo.securityConfig;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.jwtConfig.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    public CustomAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    Authentication check;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Lấy tên người dùng từ Authentication
        String username = authentication.getName();

        // Tạo JWT từ tên người dùng
        String token = jwtTokenProvider.createToken(username);

        // Đẩy token vào header của phản hồi HTTP
        response.setHeader("Authorization", "Bearer " + token);

        // Đặt mã trạng thái HTTP thành 200 OK
        response.setStatus(HttpServletResponse.SC_OK);
        this.check = authentication;

        // Bạn có thể thêm bất kỳ logic nào khác khi đăng nhập thành công tại đây
    }
    public Authentication returnCheck() {
    	return this.check;
    }
}
