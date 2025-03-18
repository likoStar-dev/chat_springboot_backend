package com.woromedia.api.task.service;

import com.woromedia.api.task.entity.User;
import com.woromedia.api.task.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final String SECRET_KEY = "your_secret_key";

    public ResponseEntity<?> login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username, username);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(401).body("Invalid username/email or password");
        }

        User user = userOptional.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username/email or password");
        }

        String token = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        user.setToken(token);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("status", "success", "result", user, "token", token));
    }

    public ResponseEntity<?> register(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        String token = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        user.setToken(token);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("status", "success", "result", user, "token", token));
    }

    public ResponseEntity<?> getMe(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Authorization header missing");
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            Long userId = Long.parseLong(claims.getSubject());
            Optional<User> userOptional = userRepository.findById(userId);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(404).body("User not found");
            }

            return ResponseEntity.ok(Map.of("status", "success", "result", userOptional.get()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }
}