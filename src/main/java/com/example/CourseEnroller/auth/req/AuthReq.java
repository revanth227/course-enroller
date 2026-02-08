package com.example.CourseEnroller.auth.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthReq {
    private String email;
    private String password;
}
