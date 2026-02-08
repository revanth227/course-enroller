package com.example.CourseEnroller.auth.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRes {
    private String token;
    private String email;
    private long expiresIn;
}
