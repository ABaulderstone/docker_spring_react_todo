package io.nology.todo_backend.category;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.Matchers.*;

import io.nology.todo_backend.auth.JwtService;
import io.nology.todo_backend.common.EndToEndTest;
import io.nology.todo_backend.config.TestDataLoader;
import io.restassured.http.ContentType;

public class CategoryEndToEndTest extends EndToEndTest {

    @Autowired
    public CategoryEndToEndTest(TestDataLoader dataLoader, JwtService jwtService) {
        super(dataLoader, jwtService);
    }

    @Test
    public void requestWithNoAuthTokenCannotAccessCategories() {
        givenNoAuthHeader().when().get("/categories").then()
                .statusCode(401);
    }

    @Test
    public void loggedInUserCanSeeTheirCategories() {
        givenUserToken().when().get("/categories")
                .then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/categories-array.json"))
                .body("size()", equalTo(1));

        givenMaxCategoryUserToken().when().get("/categories")
                .then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/categories-array.json"))
                .body("size()", equalTo(8));
    }

    @Test
    public void requestWithNoAuthTokenCannotPostToCategories() {
        givenNoAuthHeader().when().post("/categories").then().statusCode(401);
    }

    @Test
    public void loggedInUserWithSpareLimitCanPostCategories() {
        CreateCategoryDTO body = new CreateCategoryDTO();
        body.setName("test");
        body.setColor("#ffffff");
        givenUserToken().contentType(ContentType.JSON).body(body).post("/categories")
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/category-schema.json"));
    }

    @Test
    public void loggedInUserWithNoSpareLimitCannotPostCategory() {
        CreateCategoryDTO body = new CreateCategoryDTO();
        body.setName("test");
        body.setColor("#ffffff");
        // TODO: Generate better error response
        givenMaxCategoryUserToken().contentType(ContentType.JSON).body(body).post("/categories").then().statusCode(500);
    }

    @Test
    public void loggedInUserCannotPostInvalidColor() {
        CreateCategoryDTO body = new CreateCategoryDTO();
        body.setName("test");
        body.setColor("#fffff");
        givenUserToken().contentType(ContentType.JSON).body(body).post("/categories").then().statusCode(400);
    }

    @Test
    public void loggedInUserCannotPostInvalidName() {
        CreateCategoryDTO body = new CreateCategoryDTO();
        body.setName("");
        body.setColor("#ffffff");
        givenUserToken().contentType(ContentType.JSON).body(body).post("/categories").then().statusCode(400);
    }

}
