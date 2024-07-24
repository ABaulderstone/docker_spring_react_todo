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
import lombok.Getter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Getter
public abstract class EndToEndTest {
    @LocalServerPort
    public int port;

    private final TestDataLoader dataLoader;
    private final JwtService jwtService;
    private String plainUserToken;
    private String maxCategoriesToken;

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
        CustomUserDetails maxCategoryUserDetails = new CustomUserDetails(this.dataLoader.getUser("maxCategoryUser"));
        this.maxCategoriesToken = this.jwtService.generateToken(maxCategoryUserDetails);

    }

    @AfterEach
    public void tearDown() {
        this.dataLoader.clearData();
    }

    public RequestSpecification givenMaxCategoryUserToken() {
        return RestAssured.given().header("Authorization", "Bearer " + this.maxCategoriesToken);
    }

    public RequestSpecification givenUserToken() {
        return RestAssured.given().header("Authorization", "Bearer " + this.plainUserToken);
    }

    public RequestSpecification givenNoAuthHeader() {
        return RestAssured.given().header("Authorization", "Bearer ");
    }

}
