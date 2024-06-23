package io.nology.todo_backend.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryDTO data) {

        Category newCategory = this.categoryService.create(data);
        return new ResponseEntity<Category>(newCategory, HttpStatus.CREATED);

    }

}
