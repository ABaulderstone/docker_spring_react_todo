package io.nology.todo_backend.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.nology.todo_backend.common.EndToEndTest;
import io.nology.todo_backend.fixtures.AuthFixture;
import io.restassured.http.ContentType;

public class AuthEndToEndTest extends EndToEndTest<AuthFixture> {

    @Autowired
    public AuthEndToEndTest(AuthFixture fixture) {
        super(fixture);
    }

    @Test
    public void existingUserCanLoginWithCorrectPassword() {
        String email = getFixture().getUser().getEmail();
        String rawPassword = getFixture().getRawPasswords().get(email);
        LoginDTO body = new LoginDTO();
        body.setEmail(email);
        body.setPassword(rawPassword);

        givenNoAuthHeader().contentType(ContentType.JSON).body(body).post("/auth/login").then().statusCode(200);
    }

    @Test
    public void existingUserCanNotLoginWithIncorrectPassword() {
        String email = getFixture().getUser().getEmail();
        LoginDTO body = new LoginDTO();
        body.setEmail(email);
        body.setPassword("banana");
        givenNoAuthHeader().contentType(ContentType.JSON).body(body).post("/auth/login").then().statusCode(401);
    }

    @Test
    public void novelUserCanNotLogin() {
        LoginDTO body = new LoginDTO();
        body.setEmail("someguy@fake.com");
        body.setPassword("banana");
        givenNoAuthHeader().contentType(ContentType.JSON).body(body).post("/auth/login").then().statusCode(401);
    }

    @Test
    public void newUserCanRegisterWithCorrectDetails() {
        RegisterDTO body = new RegisterDTO();
        body.setEmail("new-user@test.com");
        body.setPassword("Password2!");
        body.setPasswordConfirm("Password2!");
        givenNoAuthHeader().contentType(ContentType.JSON).body(body).post("/auth/register").then().statusCode(200);
    }

    @Test
    public void newUserCanNotRegisterWithInsufficentPassword() {
        RegisterDTO body = new RegisterDTO();
        body.setEmail("new-user@test.com");
        body.setPassword("banana");
        body.setPasswordConfirm("banana");
        givenNoAuthHeader().contentType(ContentType.JSON).body(body).post("/auth/register").then().statusCode(400);
    }

    @Test
    public void newUserCanNotRegisterWithoutMatchingPasswordConfirm() {
        RegisterDTO body = new RegisterDTO();
        body.setEmail("new-user@test.com");
        body.setPassword("Password2!");
        body.setPasswordConfirm("banana");
        givenNoAuthHeader().contentType(ContentType.JSON).body(body).post("/auth/register").then().statusCode(400);
    }

    @Test
    public void newUserCanNotRegisterWithInvalidEmail() {
        RegisterDTO body = new RegisterDTO();
        body.setEmail("new-user");
        body.setPassword("Password2!");
        body.setPasswordConfirm("Password2!");
        givenNoAuthHeader().contentType(ContentType.JSON).body(body).post("/auth/register").then().statusCode(400);
    }

}
