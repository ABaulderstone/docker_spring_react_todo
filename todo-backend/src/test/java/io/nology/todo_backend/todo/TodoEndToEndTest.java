package io.nology.todo_backend.todo;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.nology.todo_backend.auth.JwtService;
import io.nology.todo_backend.common.EndToEndTest;
import io.nology.todo_backend.config.TestDataLoader;
import io.restassured.http.ContentType;

public class TodoEndToEndTest extends EndToEndTest {

    @Autowired
    public TodoEndToEndTest(TestDataLoader dataLoader, JwtService jwtService) {
        super(dataLoader, jwtService);
    }

    @Test
    public void requestWithNoAuthTokenCannotAccessTodos() {
        givenNoAuthHeader().get("/todos").then()
                .statusCode(401);
    }

    @Test
    public void loggedInUserCanAccessTheirTodos() {

        givenUserToken().get("/todos").then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/paginated-todos.json"));
    }

    @Test
    public void loggedInUserCanCreateTodo() {
        this.getDataLoader().getPlainUser().getCategories();
        CreateTodoDTO body = new CreateTodoDTO();
        // TODO: less flimsy setting category
        body.setCategoryId(1L);
        body.setTitle("Test");
        givenUserToken().contentType(ContentType.JSON).post("/todos").then().log().all().statusCode(201);
    }

}
