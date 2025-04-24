package com.farhan.Socio.service;

import com.farhan.Socio.dto.LoginDTO;
import com.farhan.Socio.dto.SignInDTO;
import com.farhan.Socio.entity.User;
import com.farhan.Socio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public ResponseEntity<String> registerUser(SignInDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists.");
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // In real apps, hash it!
        user.setAdmin(userDTO.getEmail().endsWith("@socio.com"));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    public ResponseEntity<String> loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if (user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.ok("Login successful.");
        }
        return ResponseEntity.status(401).body("Invalid credentials.");
    }

    public ResponseEntity<String> toggleProfileVisibility(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return ResponseEntity.badRequest().body("User not found.");
        user.setPrivate(!user.isPrivate());
        userRepository.save(user);
        return ResponseEntity.ok("Profile visibility updated.");
    }

    public ResponseEntity<String> makeAdmin(String email) {
        if (!email.endsWith("@socio.com")) {
            return ResponseEntity.badRequest().body("Only @socio.com emails can be admins.");
        }
        User user = userRepository.findByEmail(email);
        if (user == null) return ResponseEntity.badRequest().body("User not found.");
        user.setAdmin(true);
        userRepository.save(user);
        return ResponseEntity.ok("User promoted to admin.");
    }
}
