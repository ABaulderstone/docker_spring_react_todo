package io.nology.todo_backend.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.nology.todo_backend.auth.CustomUserDetails;
import io.nology.todo_backend.auth.JwtService;
import io.nology.todo_backend.config.TestDataLoader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class EndToEndTest {
    @LocalServerPort
    public int port;

    private final TestDataLoader dataLoader;
    private final JwtService jwtService;
    private String plainUserToken;

    @Autowired
    public EndToEndTest(TestDataLoader dataLoader, JwtService jwtService) {
        this.dataLoader = dataLoader;
        this.jwtService = jwtService;
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        this.dataLoader.clearData();
        this.dataLoader.loadData();
        CustomUserDetails userDetails = new CustomUserDetails(this.dataLoader.getPlainUser());
        this.plainUserToken = this.jwtService.generateToken(userDetails);

    }

    @AfterEach
    public void tearDown() {

    }

    public RequestSpecification givenUserToken() {
        return RestAssured.given().header("Authorization", "Bearer " + this.plainUserToken);
    }

}
