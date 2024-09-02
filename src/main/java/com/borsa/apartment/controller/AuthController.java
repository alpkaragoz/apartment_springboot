package com.borsa.apartment.controller;

import com.borsa.apartment.dto.TokenResponseDto;
import com.borsa.apartment.model.User;
import com.borsa.apartment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Authenticate user and generate token", description = "Validate user credentials and generate a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated and token generated."),
            @ApiResponse(responseCode = "401", description = "Invalid credentials provided.", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found.", content = @Content)
    })    @PostMapping("/tokens")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody User requestUser) {
        return ResponseEntity.ok().body(userService.authenticateUser(requestUser));
    }

    @Operation(summary = "Validate JWT token", description = "Check the validity of a provided JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid."),
            @ApiResponse(responseCode = "401", description = "Token is invalid or expired.", content = @Content)
    })    @GetMapping("/tokens")
    public ResponseEntity<Void> validateToken() {
        return ResponseEntity.ok().build();
    }
}
