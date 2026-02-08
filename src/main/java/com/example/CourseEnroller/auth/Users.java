package com.example.CourseEnroller.auth;

import com.example.CourseEnroller.entrollment.Enrollment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;



    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
