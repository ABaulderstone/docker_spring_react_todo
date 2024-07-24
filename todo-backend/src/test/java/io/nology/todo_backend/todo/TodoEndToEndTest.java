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
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String formattedDate = tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        CreateTodoDTO body = new CreateTodoDTO();
        // TODO: less flimsy setting category
        body.setCategoryId(userWithcats.getCategories().get(0).getId());
        body.setTitle("Test");
        body.setDueDate(formattedDate);
        givenUserToken().contentType(ContentType.JSON).body(body).post("/todos").then().log().all().statusCode(201);
    }

}
