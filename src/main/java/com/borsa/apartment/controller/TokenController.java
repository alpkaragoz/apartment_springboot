package com.borsa.apartment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:4200")
public class TokenController {

    // If the call passed security check, it is valid.
    @GetMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken() {
        return ResponseEntity.ok(Collections.singletonMap("message", "Token is valid"));
    }
}