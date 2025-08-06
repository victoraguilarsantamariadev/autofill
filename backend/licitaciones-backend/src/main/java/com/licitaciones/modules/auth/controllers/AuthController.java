package com.licitaciones.modules.auth.controllers;

import com.licitaciones.modules.auth.services.AuthService;
import com.licitaciones.modules.user.entities.User;
import com.licitaciones.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/callback")
    public ResponseEntity<User> handleAuthCallback(@AuthenticationPrincipal Jwt jwt) {
        User user = authService.syncUserFromAuth0(jwt);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal Jwt jwt) {
        User user = authService.getUserProfile(jwt.getSubject());
        return ResponseEntity.ok(user);
    }
    @PostMapping("/test-user")
    public ResponseEntity<User> testCreateUser() {
        User user = new User();
        user.setAuth0Id("test-123");
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        user = userRepository.save(user);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/test-auth0")
    public ResponseEntity<User> testAuth0Simulation() {
        // Simular lo que vendría de Auth0
        User user = new User();
        user.setAuth0Id("auth0|" + System.currentTimeMillis());
        user.setEmail("test.user@licitaciones.com");
        user.setName("Usuario Simulado Auth0");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Simular lógica de sincronización (como en AuthService)
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            User existingUser = existing.get();
            existingUser.setName(user.getName());
            existingUser.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(userRepository.save(existingUser));
        } else {
            return ResponseEntity.ok(userRepository.save(user));
        }
    }
}