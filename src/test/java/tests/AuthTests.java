package tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @Test
    public void loginTest() {

        String requestBody = """
            {
              "username": "mor_2314",
              "password": "83r5^_"
            }
            """;

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(201)
                .body("token", notNullValue());
    }
}