package io.nology.todo_backend.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.nology.todo_backend.auth.JwtService;
import io.nology.todo_backend.common.EndToEndTest;
import io.nology.todo_backend.config.TestDataLoader;

public class UserEndToEndTest extends EndToEndTest {

    @Autowired
    public UserEndToEndTest(TestDataLoader dataLoader, JwtService jwtService) {
        super(dataLoader, jwtService);
    }

    @Test
    public void userWithTokenCanAccessCurrentPage() {
        givenUserToken().when().get("/users/current").then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }

    @Test
    public void userWithNoAuthTokenCannotAccessCurrentPage() {
        givenNoAuthHeader().when().get("/users/current").then().statusCode(401);
    }

}
