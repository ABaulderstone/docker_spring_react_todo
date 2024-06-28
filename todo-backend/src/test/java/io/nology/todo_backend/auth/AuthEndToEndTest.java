package io.nology.todo_backend.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.nology.todo_backend.common.EndToEndTest;
import io.nology.todo_backend.config.TestDataLoader;
import io.nology.todo_backend.user.User;
import io.restassured.http.ContentType;

public class AuthEndToEndTest extends EndToEndTest {

    @Autowired
    public AuthEndToEndTest(TestDataLoader dataLoader, JwtService jwtService) {
        super(dataLoader, jwtService);

    }

    @Test
    public void existingUserCanLoginWithCorrectPassword() {
        User plainUser = getDataLoader().getUser("plainUser");
        String password = getDataLoader().getRawPassword("plainUser");
        LoginDTO body = new LoginDTO();
        body.setEmail(plainUser.getEmail());
        body.setPassword(password);

        givenNoAuthHeader().contentType(ContentType.JSON).body(body).post("/auth/login").then().statusCode(200);
    }

    @Test
    public void existingUserCanNotLoginWithIncorrectPassword() {
        User plainUser = getDataLoader().getUser("plainUser");
        LoginDTO body = new LoginDTO();
        body.setEmail(plainUser.getEmail());
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
