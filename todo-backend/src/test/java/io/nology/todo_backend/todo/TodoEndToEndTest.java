package io.nology.todo_backend.todo;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.nology.todo_backend.common.EndToEndTest;
import io.nology.todo_backend.fixtures.TodoFixture;
import io.nology.todo_backend.user.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TodoEndToEndTest extends EndToEndTest<TodoFixture> {

    @Autowired
    public TodoEndToEndTest(TodoFixture fixture) {
        super(fixture);
    }

    @Test
    public void requestWithNoAuthTokenCannotAccessTodos() {
        givenNoAuthHeader().get("/todos").then()
                .statusCode(401);
    }

    @Test
    public void loggedInUserCanAccessTheirTodos() {

        givenUserToken(getFixture().getUser().getEmail()).get("/todos").then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/paginated-todos.json"));
    }

    @Test
    public void loggedInUserCanCreateTodo() {

        LocalDate futureDate = LocalDate.now().plusDays(7);
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        CreateTodoDTO body = new CreateTodoDTO();
        Long categoryId = getFixture().fetchCategoriesForUser(getFixture().getUser()).get(0).getId();
        body.setCategoryId(categoryId);
        body.setTitle("Test");
        body.setDueDate(formattedDate);

        Response response = givenUserToken(getFixture().getUser().getEmail())
                .contentType(ContentType.JSON)
                .body(body)
                .post("/todos");

        response.then().log().all().statusCode(201);

    }
}
