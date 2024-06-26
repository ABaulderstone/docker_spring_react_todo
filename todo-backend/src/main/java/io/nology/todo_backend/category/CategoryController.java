package io.nology.todo_backend.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryDTO data) throws Exception {

        Category newCategory = this.categoryService.create(data);
        return new ResponseEntity<Category>(newCategory, HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<List<Category>> findAll() {
        List<Category> userCategories = this.categoryService.findCurrentUserCategories();
        return new ResponseEntity<List<Category>>(userCategories, HttpStatus.OK);
    }

}
