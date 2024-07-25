package io.nology.todo_backend.fixtures;

import org.springframework.stereotype.Component;

import io.nology.todo_backend.user.User;
import lombok.Getter;

@Component
public class CategoryFixture extends BaseFixture {
    @Getter
    private User userWithMaxCategories;
    @Getter
    private User userWithOneCategory;

    @Getter
    private User userWithNoCategories;

    @Override
    public void setup() {
        userWithMaxCategories = createUserWithToken();
        createUniqueCategoriesForUser(8, userWithMaxCategories);
        userWithOneCategory = createUserWithToken();
        createUniqueCategoriesForUser(1, userWithOneCategory);
        userWithNoCategories = createUserWithToken();

    }

}
