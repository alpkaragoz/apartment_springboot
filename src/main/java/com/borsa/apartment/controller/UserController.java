package com.borsa.apartment.controller;

import com.borsa.apartment.model.User;
import com.borsa.apartment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User requestUser) {
        User savedUser = userService.saveUser(requestUser);
        Map<String, Object> responseBody = new HashMap<>();
        if (savedUser == null) {
            responseBody.put("message", "Email already in-use.");
            return ResponseEntity.status(400).body(responseBody);
        }
        responseBody.put("message", "Registration successful.");
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User requestUser) {
        User user = userService.authenticateUser(requestUser);
        Map<String, Object> responseBody = new HashMap<>();
        if (user == null) {
            responseBody.put("message", "Invalid credentials.");
            return ResponseEntity.status(401).body(responseBody);
        }
        responseBody.put("message", "Authentication successful.");
        responseBody.put("token", user.getToken());
        return ResponseEntity.ok(responseBody);
    }
}