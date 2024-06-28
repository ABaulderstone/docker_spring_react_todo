package io.nology.todo_backend.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.nology.todo_backend.common.EndToEndTest;
import io.nology.todo_backend.config.TestDataLoader;
import io.nology.todo_backend.user.User;
import io.restassured.http.ContentType;

public class AuthEndToEnd extends EndToEndTest {

    @Autowired
    public AuthEndToEnd(TestDataLoader dataLoader, JwtService jwtService) {
        super(dataLoader, jwtService);

    }

    @Test
    public void existingUserCanLogin() {
        User plainUser = getDataLoader().getUser("plainUser");
        String password = getDataLoader().getRawPassword("plainUser");
        LoginDTO body = new LoginDTO();
        body.setEmail(plainUser.getEmail());
        body.setPassword(password);

        givenNoAuthHeader().contentType(ContentType.JSON).body(body).post("/auth/login").then().statusCode(200);

    }

}
