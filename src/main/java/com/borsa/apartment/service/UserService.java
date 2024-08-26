package com.borsa.apartment.service;

import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.dto.TokenResponseDto;
import com.borsa.apartment.exception.InvalidCredentialsException;
import com.borsa.apartment.exception.UserAlreadyExistsException;
import com.borsa.apartment.exception.UserNotFoundException;
import com.borsa.apartment.model.User;
import com.borsa.apartment.repo.UserRepository;
import com.borsa.apartment.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public MessageResponseDto saveUser(User requestUser) {
        String email = requestUser.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with given email already exists.");
        }
        ValidationUtil.validateRegister(requestUser);
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Registration successful.");
        requestUser.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        userRepository.save(requestUser);
        LOGGER.info("New user created with email: {}", email);
        return responseDto;
    }

    public TokenResponseDto authenticateUser(User requestUser) {
        String email = requestUser.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Given email or password is wrong."));
        if (passwordEncoder.matches(requestUser.getPassword(), user.getPassword())) {
            TokenResponseDto responseDto = new TokenResponseDto();
            responseDto.setMessage("Authentication successful.");
            responseDto.setToken(jwtService.generateToken(user.getId().toString()));
            responseDto.setId(user.getId().toString());
            LOGGER.info("User logged in. Email: {}", email);
            return responseDto;
        } else {
            throw new InvalidCredentialsException("Given email or password is wrong.");
        }
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}