package io.nology.todo_backend.category;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.nology.todo_backend.common.BaseService;
import io.nology.todo_backend.user.User;
import jakarta.validation.Valid;

@Service
public class CategoryService extends BaseService {
    private final CategoryRepository repo;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Category create(@Valid CreateCategoryDTO data) {
        Category newCategory = mapper.map(data, Category.class);
        User owner = new User();
        Long currentUserId = getCurrentUserId();
        owner.setId(currentUserId);
        newCategory.setUser(owner);
        return this.repo.save(newCategory);
    }

}
