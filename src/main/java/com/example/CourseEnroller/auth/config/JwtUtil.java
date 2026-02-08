package com.example.CourseEnroller.auth.config;

import com.example.CourseEnroller.auth.res.LoginRes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret ;

    private String createTtoken(String email) {
        Map<String, Object> claims = new HashMap<>();
        long expirationTime = 1000 * 60 * 60 * 3;
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getThekey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public long extractExpiration(String token) {
        return extractAllClaims(token).getExpiration().toInstant().getEpochSecond();
    }

    private SecretKey getThekey() {
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getThekey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validate(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpirations(token).before(new Date());
    }

    private Date extractExpirations(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public LoginRes generate(String email) {
        String token = createTtoken(email);
        long expiresIn = extractExpiration(token);
        LoginRes loginRes = new LoginRes(token, email, expiresIn);
        return loginRes;
    }
}
