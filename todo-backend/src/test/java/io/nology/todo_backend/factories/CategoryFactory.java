package io.nology.todo_backend.factories;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import io.nology.todo_backend.category.Category;
import io.nology.todo_backend.category.CategoryRepository;
import io.nology.todo_backend.user.User;

@Component
public class CategoryFactory extends BaseFactory<Category> {
    private CategoryRepository categoryRepository;

    public CategoryFactory(Faker faker, CategoryRepository categoryRepository) {
        super(faker);
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create() {
        Category newCategory = new Category();
        newCategory.setColor(faker.color().hex(true));
        newCategory.setName(faker.hacker().noun());
        return newCategory;

    }

    public void save(Category category) {
        this.categoryRepository.save(category);
    }

}
