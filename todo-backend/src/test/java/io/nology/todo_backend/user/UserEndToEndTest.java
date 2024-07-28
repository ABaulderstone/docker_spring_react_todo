package io.nology.todo_backend.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.nology.todo_backend.common.EndToEndTest;
import io.nology.todo_backend.fixtures.UserFixture;

public class UserEndToEndTest extends EndToEndTest<UserFixture> {

    @Autowired
    public UserEndToEndTest(UserFixture fixture) {
        super(fixture);
    }

    @Test
    public void userWithTokenCanAccessCurrentPage() {
        givenUserToken(getFixture().getUser().getEmail()).get("/users/current").then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }

    @Test
    public void requestWithNoAuthTokenCannotAccessCurrentPage() {
        givenNoAuthHeader().get("/users/current").then().statusCode(401);
    }

}
