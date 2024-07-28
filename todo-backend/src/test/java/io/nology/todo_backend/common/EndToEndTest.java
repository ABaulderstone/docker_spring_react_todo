package io.nology.todo_backend.common;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.nology.todo_backend.auth.JwtService;

import io.nology.todo_backend.fixtures.BaseFixture;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Getter
@TestInstance(Lifecycle.PER_CLASS)
public abstract class EndToEndTest<T extends BaseFixture> {
    @LocalServerPort
    public int port;

    private final T fixture;

    @Autowired
    public EndToEndTest(T fixture) {
        this.fixture = fixture;
    }

    @BeforeAll
    public void setUp() {
        RestAssured.port = port;
        this.fixture.setup();

    }

    @AfterAll
    public void tearDown() {
        this.fixture.tearDown();
    }

    public RequestSpecification givenUserToken(String email) {
        return RestAssured.given().header("Authorization", "Bearer " + fixture.getUserToken(email));
    }

    public RequestSpecification givenNoAuthHeader() {
        return RestAssured.given().header("Authorization", "Bearer ");
    }

}
