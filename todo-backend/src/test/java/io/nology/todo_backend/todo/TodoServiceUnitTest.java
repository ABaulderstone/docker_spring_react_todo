package io.nology.todo_backend.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import io.nology.todo_backend.category.Category;
import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserService;

public class TodoServiceUnitTest {
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private UserService userService;
    @Mock
    private ModelMapper mapper;

    @Spy
    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTodo_success() throws Exception {
        Todo mockTodo = new Todo();
        CreateTodoDTO mockCreateTodoDTO = new CreateTodoDTO();
        mockCreateTodoDTO.setCategoryId(1L);
        Long mockUserId = 1L;
        User mockUser = new User();
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockUser.setId(mockUserId);
        List<Category> userCategories = Arrays.asList(mockCategory);
        mockUser.setCategories(userCategories);

        when(mapper.map(mockCreateTodoDTO, Todo.class)).thenReturn(mockTodo);
        doReturn(mockUserId).when(todoService).getCurrentUserId();
        when(userService.loadById(mockUserId)).thenReturn(Optional.of(mockUser));
        when(todoRepository.save(mockTodo)).thenReturn(mockTodo);

        Todo result = todoService.createTodo(mockCreateTodoDTO);
        assertNotNull(result);
        assertEquals(mockTodo, result);

        verify(todoRepository).save(mockTodo);

    }

    @Test
    void createTodo_categoryDoesNotBelongToUser() throws Exception {
        Todo mockTodo = new Todo();
        CreateTodoDTO mockCreateTodoDTO = new CreateTodoDTO();
        mockCreateTodoDTO.setCategoryId(2L);
        Long mockUserId = 1L;
        User mockUser = new User();
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockUser.setId(mockUserId);
        List<Category> userCategories = Arrays.asList(mockCategory);
        mockUser.setCategories(userCategories);

        when(mapper.map(mockCreateTodoDTO, Todo.class)).thenReturn(mockTodo);
        doReturn(mockUserId).when(todoService).getCurrentUserId();
        when(userService.loadById(mockUserId)).thenReturn(Optional.of(mockUser));

        assertThrows(Exception.class, () -> todoService.createTodo(mockCreateTodoDTO));

        verify(todoRepository, never()).save(mockTodo);
    }

}
