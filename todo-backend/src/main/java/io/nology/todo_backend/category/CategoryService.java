package io.nology.todo_backend.category;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.nology.todo_backend.common.BaseService;
import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserService;
import jakarta.validation.Valid;

@Service
public class CategoryService extends BaseService {
    private final CategoryRepository repo;
    private final UserService userService;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository repo, UserService userService, ModelMapper mapper) {
        this.repo = repo;
        this.userService = userService;
        this.mapper = mapper;
    }

    public Category create(@Valid CreateCategoryDTO data) throws Exception {
        Category newCategory = mapper.map(data, Category.class);
        Long currentUserId = getCurrentUserId();
        User owner = this.userService.loadById(currentUserId).orElseThrow(() -> new Exception("No user found"));
        if (owner.getCategories().size() == 8) {
            throw new Exception("You cannot register more than 8 categories");
        }
        owner.setId(currentUserId);
        newCategory.setUser(owner);
        return this.repo.save(newCategory);
    }

    public List<Category> findCurrentUserCategories() {
        return this.repo.findbyUserId(getCurrentUserId());
    }

}
