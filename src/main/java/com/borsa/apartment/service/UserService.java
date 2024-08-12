package com.borsa.apartment.service;

import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.model.User;
import com.borsa.apartment.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public User saveUser(User requestUser) {
        String userEmail = requestUser.getEmail();
        if (userRepository.findByEmail(userEmail) != null) {
            return null; // Email already registered
        }
        requestUser.setToken(jwtService.generateToken(userEmail));
        requestUser.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        return userRepository.save(requestUser);
    }

    public User authenticateUser(User requestUser) {
        String userEmail = requestUser.getEmail();
        User user = userRepository.findByEmail(userEmail);
        if (user != null && passwordEncoder.matches(requestUser.getPassword(), user.getPassword())) {
            String newToken = jwtService.generateToken(userEmail);
            user.setToken(newToken);
            userRepository.updateToken(userEmail, newToken);
            return user;
        }
        return null;
    }

    public User findUserFromToken(String token) {
        String email = jwtService.extractEmail(token);
        return userRepository.findByEmail(email);
    }

    public List<ApartmentListing> getApartmentListings(User user) {
        return user.getApartmentListings();
    }
}