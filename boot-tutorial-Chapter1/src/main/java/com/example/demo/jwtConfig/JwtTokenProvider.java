package com.example.demo.jwtConfig;


import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example.demo.authenticationProvider.CustomAuthenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

    // Thời gian hiệu lực của token (tính bằng miligiây)
    @Value("${jwt.expiration}")
    private long expirationTime;

    // Khóa bí mật (được lấy từ file properties hoặc môi trường)
    @Value("${jwt.secret}")
    private String secretKey;

    // Đối tượng UserDetailsService để lấy thông tin người dùng
    private final UserDetailsService userDetailsService;

    private SecretKey secretKeyObject;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void init() {
        // Tạo đối tượng SecretKey từ chuỗi khóa bí mật
        secretKeyObject = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Tạo JWT từ tên người dùng
    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKeyObject, SignatureAlgorithm.HS256)
                .compact();
    }

    // Lấy token từ header Authorization của HTTP request
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Xác thực token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKeyObject)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // Token đã hết hạn
            return false;
        } catch (Exception e) {
            // Token không hợp lệ
            return false;
        }
    }

    // Tạo Authentication từ token
    public Authentication getAuthentication(String token) {
        // Lấy thông tin người dùng từ token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKeyObject)
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        // Lấy tên người dùng từ claims
        String username = claims.getSubject();

        // Lấy thông tin người dùng từ UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Tạo đối tượng Authentication
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

