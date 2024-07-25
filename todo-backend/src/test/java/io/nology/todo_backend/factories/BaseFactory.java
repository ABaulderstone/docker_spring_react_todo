package io.nology.todo_backend.factories;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.javafaker.Faker;

public abstract class BaseFactory<T> {
    protected final Faker faker;

    @Autowired
    public BaseFactory(Faker faker) {
        this.faker = faker;
    }

    public abstract T create();

    public abstract T createAndSave();

}
