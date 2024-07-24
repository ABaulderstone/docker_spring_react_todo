package io.nology.todo_backend.todo;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.nology.todo_backend.auth.JwtService;
import io.nology.todo_backend.category.Category;
import io.nology.todo_backend.common.EndToEndTest;
import io.nology.todo_backend.config.TestDataLoader;
import io.nology.todo_backend.user.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

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
        User userWithcats = this.getDataLoader().getPlainUserWithCategories();
        System.out.println("User ID: " + userWithcats.getId());
        System.out.println("Categories: " + userWithcats.getCategories());

        LocalDate futureDate = LocalDate.now().plusDays(7);
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("Due date set to: " + formattedDate);

        CreateTodoDTO body = new CreateTodoDTO();
        body.setCategoryId(userWithcats.getCategories().get(0).getId());
        body.setTitle("Test");
        body.setDueDate(formattedDate);

        Response response = givenUserToken()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/todos");

        System.out.println("Response status: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        response.then().log().all().statusCode(201);

    }
}
