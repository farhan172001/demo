package com.farhan.Socio.controller;

import com.farhan.Socio.dto.LoginDTO;
import com.farhan.Socio.dto.SignInDTO;
import com.farhan.Socio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignInDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return userService.loginUser(loginDTO);
    }

    @PutMapping("/profile/visibility")
    public ResponseEntity<String> toggleVisibility(@RequestParam String email) {
        return userService.toggleProfileVisibility(email);
    }

    @PostMapping("/make-admin")
    public ResponseEntity<String> makeAdmin(@RequestParam String email) {
        return userService.makeAdmin(email);
    }

}
