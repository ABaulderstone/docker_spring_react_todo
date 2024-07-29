package io.nology.todo_backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import io.nology.todo_backend.factories.UserFactory;

public class UserServiceUnitTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, modelMapper);
    }

    @Test
    void loadUserByEmail_success() {
        User mockUser = new User();
        mockUser.setEmail("real_user@test.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        Optional<User> result = userService.loadUserByEmail(mockUser.getEmail());
        assertEquals(mockUser, result.get());
        verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
    }

    @Test
    void loadUserByEmail_failure() {
        User mockUser = new User();
        mockUser.setEmail("real_user@test.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        Optional<User> result = userService.loadUserByEmail(mockUser.getEmail());
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
    }
}
