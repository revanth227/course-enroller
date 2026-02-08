package com.example.CourseEnroller.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users,Long> {
    Users findByEmail(String username);

    boolean existsByEmail(String email);
    // Optional<Users> findByEmail(String username);
}


