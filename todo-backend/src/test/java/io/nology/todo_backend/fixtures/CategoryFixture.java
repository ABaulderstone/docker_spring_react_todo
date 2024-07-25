package io.nology.todo_backend.fixtures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.nology.todo_backend.category.Category;
import io.nology.todo_backend.user.User;
import lombok.Getter;

public class CategoryFixture extends BaseFixture {
    @Getter
    private User userWithMaxCategories;
    @Getter
    private User userWithOneCategory;

    @Override
    public void setup() {
        userWithMaxCategories = createUserWithToken();
        createUniqueCategoriesForUser(8, userWithMaxCategories);
        userWithOneCategory = createUserWithToken();
        createUniqueCategoriesForUser(1, userWithOneCategory);

    }

}
