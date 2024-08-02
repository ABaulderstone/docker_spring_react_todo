package io.nology.todo_backend.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
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
        doReturn(mockUserId).when(categoryService).getCurrentUserId();
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.create(categoryDTO);
        assertNotNull(result);
        assertEquals(category, result);
        verify(categoryRepository).save(category);

    }

    @Test
    void create_maxCategories_failure() throws Exception {
        CreateCategoryDTO createCategoryDTO = new CreateCategoryDTO();
        Category category = new Category();
        User mockUser = new User();
        Long mockUserId = 1L;
        mockUser.setId(mockUserId);
        ArrayList<Category> userCategories = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Category cat = new Category();
            cat.setColor("#fffff" + i);
            cat.setName("Cat " + i);
            userCategories.add(cat);
        }
        mockUser.setCategories(userCategories);
        when(mapper.map(createCategoryDTO, Category.class)).thenReturn(category);
        doReturn(mockUserId).when(categoryService).getCurrentUserId();
        when(userService.loadById(mockUserId)).thenReturn(Optional.of(mockUser));

        assertThrows(Exception.class, () -> categoryService.create(createCategoryDTO));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void findCurrentUserCategories_success() {
        User mockUser = new User();
        Long mockUserId = 1L;
        mockUser.setId(mockUserId);
        ArrayList<Category> userCategories = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Category cat = new Category();
            cat.setColor("#fffff" + i);
            cat.setName("Cat " + i);
            userCategories.add(cat);
        }
        mockUser.setCategories(userCategories);
        doReturn(mockUserId).when(categoryService).getCurrentUserId();
        when(categoryRepository.findbyUserId(mockUserId)).thenReturn(userCategories);

        List<Category> result = categoryService.findCurrentUserCategories();
        assertEquals(userCategories, result);
        verify(categoryRepository).findbyUserId(mockUserId);
    }

}
