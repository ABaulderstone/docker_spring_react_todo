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
    private User user;

    @Autowired
    public TodoEndToEndTest(TodoFixture fixture) {
        super(fixture);
        user = getFixture().getUser();
    }

    @Test
    public void requestWithNoAuthTokenCannotAccessTodos() {
        givenNoAuthHeader().get("/todos").then()
                .statusCode(401);
    }

    @Test
    public void loggedInUserCanAccessTheirTodos() {

        givenUserToken(user.getEmail()).get("/todos").then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/paginated-todos.json"));
    }

    @Test
    public void loggedInUserCanCreateTodo() {

        LocalDate futureDate = LocalDate.now().plusDays(7);
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        CreateTodoDTO body = new CreateTodoDTO();
        body.setCategoryId(user.getCategories().get(0).getId());
        body.setTitle("Test");
        body.setDueDate(formattedDate);

        Response response = givenUserToken(user.getEmail())
                .contentType(ContentType.JSON)
                .body(body)
                .post("/todos");

        response.then().log().all().statusCode(201);

    }
}
