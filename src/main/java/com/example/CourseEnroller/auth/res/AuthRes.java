package com.example.CourseEnroller.auth.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthRes {
    private Long id;
    private String email;
    private String message;
}
