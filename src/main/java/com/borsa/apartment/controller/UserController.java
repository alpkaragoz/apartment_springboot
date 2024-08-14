package com.borsa.apartment.controller;

import com.borsa.apartment.dto.LoginResponseDto;
import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.model.User;
import com.borsa.apartment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> registerUser(@RequestBody User requestUser) {
        User savedUser = userService.saveUser(requestUser);
        MessageResponseDto responseBody = new MessageResponseDto();
        if (savedUser == null) {
            responseBody.setMessage("Email already in-use.");
            return ResponseEntity.status(400).body(responseBody);
        }
        responseBody.setMessage("Registration successful.");
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody User requestUser) {
        User user = userService.authenticateUser(requestUser);
        LoginResponseDto responseBody = new LoginResponseDto();
        if (user == null) {
            responseBody.setMessage("Invalid credentials.");
            return ResponseEntity.status(401).body(responseBody);
        }
        responseBody.setMessage("Authentication successful.");
        responseBody.setToken(user.getToken());
        return ResponseEntity.ok(responseBody);
    }
}