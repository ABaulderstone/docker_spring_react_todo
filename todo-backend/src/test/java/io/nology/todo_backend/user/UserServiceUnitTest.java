package io.nology.todo_backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

public class UserServiceUnitTest {

    @Spy
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        String badEmail = "bad_email@test.com";
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        Optional<User> result = userService.loadUserByEmail(badEmail);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByEmail(badEmail);
    }

    @Test
    void loadById_success() {
        User mockUser = new User();
        mockUser.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        Optional<User> result = userService.loadById(1L);
        assertEquals(mockUser, result.get());
        verify(userRepository).findById(1L);
    }

    @Test
    void loadById_failure() {
        Long badId = 1000L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<User> result = userService.loadById(badId);
        assertTrue(result.isEmpty());
    }

    @Test
    void currentUser_success() throws Exception {
        Long mockUserId = 1L;
        User mockUser = new User();
        mockUser.setId(mockUserId);
        doReturn(mockUserId).when(userService).getCurrentUserId();
        when(userRepository.findById(mockUserId)).thenReturn(Optional.of(mockUser));
        User result = userService.currentUser();
        assertEquals(mockUser, result);
        verify(userService).getCurrentUserId();
        verify(userRepository).findById(mockUserId);

    }

    @Test
    void currentUser_failure() throws Exception {
        Long badId = 1000L;

        doReturn(badId).when(userService).getCurrentUserId();
        when(userRepository.findById(badId)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> userService.currentUser());
        verify(userService).getCurrentUserId();
        verify(userRepository).findById(badId);
    }
}
