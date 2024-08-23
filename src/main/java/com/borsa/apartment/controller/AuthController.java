package com.borsa.apartment.controller;

import com.borsa.apartment.dto.TokenResponseDto;
import com.borsa.apartment.model.User;
import com.borsa.apartment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/tokens")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody User requestUser) {
        return ResponseEntity.ok().body(userService.authenticateUser(requestUser));
    }

    @GetMapping("/tokens")
    public ResponseEntity<Void> validateToken() {
        return ResponseEntity.ok().build();
    }
}
