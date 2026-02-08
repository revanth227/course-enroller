package com.example.CourseEnroller.auth;

import com.example.CourseEnroller.auth.req.AuthReq;
import com.example.CourseEnroller.auth.req.LoginReq;
import com.example.CourseEnroller.auth.res.AuthRes;
import com.example.CourseEnroller.auth.res.LoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/api/auth/register")
    public ResponseEntity<AuthRes> register(@RequestBody AuthReq request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.addingNewUser(request));
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq req) {
        return ResponseEntity.ok(authService.verifyingUser(req));
    }

    @GetMapping("/go")
    public String test() {
        return "333";
    }


}
