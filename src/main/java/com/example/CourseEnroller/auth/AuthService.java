package com.example.CourseEnroller.auth;

import com.example.CourseEnroller.auth.config.JwtUtil;
import com.example.CourseEnroller.auth.req.AuthReq;
import com.example.CourseEnroller.auth.req.LoginReq;
import com.example.CourseEnroller.auth.res.AuthRes;
import com.example.CourseEnroller.auth.res.LoginRes;
import com.example.CourseEnroller.exception.ConflictException;
import com.example.CourseEnroller.exception.ForbiddenException;
import com.example.CourseEnroller.exception.ResourceNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil util;

    public AuthService(@Lazy AuthenticationManager authenticationManager, UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil util) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.util = util;
    }
    private AuthRes convertAuthResponse(Users users) {
        return new AuthRes(users.getId(), users.getEmail(), "User Registered");
    }
    public AuthRes addingNewUser(AuthReq req) {
        String password = passwordEncoder.encode(req.getPassword());
        Users users = new Users(req.getEmail(), password);
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        userRepo.save(users);
        return convertAuthResponse(users);
    }
    public LoginRes verifyingUser(LoginReq req) {
        Users users = userRepo.findByEmail(req.getEmail());
        if (users == null) {
            throw new ResourceNotFoundException("No Email Found");
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        if (authentication.isAuthenticated()) {
            return util.generate(req.getEmail());
        }

        throw new ForbiddenException("Authentication failed");
    }
}
