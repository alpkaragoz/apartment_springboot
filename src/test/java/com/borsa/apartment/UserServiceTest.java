package com.borsa.apartment;

import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.exception.UserNotFoundException;
import com.borsa.apartment.model.User;
import com.borsa.apartment.repo.UserRepository;
import com.borsa.apartment.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testSaveUserSuccess() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        MessageResponseDto response = userService.saveUser(user);

        assertEquals("Registration successful.", response.getMessage());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserSuccess() {
        User user = new User();
        user.setId(23L);
        user.setEmail("alpkaragoz3@gmail.com");

        when(userRepository.findById(23L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUser(23L);

        assertEquals("alpkaragoz3@gmail.com", foundUser.getEmail());
    }

    @Test
    void testGetUserNotFound() {
        when(userRepository.findById(0L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(0L));
    }
}
