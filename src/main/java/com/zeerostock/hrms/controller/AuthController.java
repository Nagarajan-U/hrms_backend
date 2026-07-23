package com.zeerostock.hrms.controller;

import com.zeerostock.hrms.dto.AuthResponse;
import com.zeerostock.hrms.dto.LoginRequest;
import com.zeerostock.hrms.model.User;
import com.zeerostock.hrms.repository.UserRepository;
import com.zeerostock.hrms.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        if (userOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
            User user = userOpt.get();
            String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
            
            // Mask password before returning user object
            user.setPassword(null); 
            return ResponseEntity.ok(new AuthResponse(token, user));
        }

        return ResponseEntity.status(401).body("Invalid email or password");
    }
}