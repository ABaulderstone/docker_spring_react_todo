package io.nology.todo_backend.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserService;

public class CategoryServiceUnitTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper mapper;

    @Spy
    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_success() throws Exception {
        Long mockUserId = 1L;
        User mockUser = new User();
        mockUser.setId(mockUserId);
        mockUser.setCategories(new ArrayList<Category>() {
        });
        CreateCategoryDTO categoryDTO = new CreateCategoryDTO();
        Category category = new Category();

        when(mapper.map(categoryDTO, Category.class)).thenReturn(category);
        when(userService.loadById(mockUserId)).thenReturn(Optional.of(mockUser));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        doReturn(mockUserId).when(categoryService).getCurrentUserId();

        Category result = categoryService.create(categoryDTO);
        assertNotNull(result);
        assertEquals(category, result);
        verify(categoryRepository).save(category);

    }

}
