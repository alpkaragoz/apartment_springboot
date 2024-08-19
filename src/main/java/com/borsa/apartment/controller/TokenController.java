package com.borsa.apartment.controller;

import com.borsa.apartment.dto.MessageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:4200")
public class TokenController {

    // If the call passed security check, it is valid.
    @GetMapping("/validate-token")
    public ResponseEntity<MessageResponseDto> validateToken() {
        MessageResponseDto responseBody = new MessageResponseDto();
        responseBody.setMessage("Token valid.");
        return ResponseEntity.ok().body(responseBody);
    }
}