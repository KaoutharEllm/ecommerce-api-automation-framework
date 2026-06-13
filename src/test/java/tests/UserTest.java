package tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @Test
    public void getAllUsersTest() {

        given()
                .when()
                .get("/users")
                .then()
                .log().body()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void getSingleUserTest() {

        given()
                .when()
                .get("/users/1")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("username", notNullValue())
                .body("email", notNullValue())
                .body("password", notNullValue());
    }

    @Test
    public void addNewUserTest() {

        String requestBody = """
                {
                  "username": "testuser",
                  "email": "testuser@gmail.com",
                  "password": "test123"
                }
                """;

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void updateUserTest() {

        String requestBody = """
                {
                  "username": "updateduser",
                  "email": "updateduser@gmail.com",
                  "password": "updated123"
                }
                """;

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/users/1")
                .then()
                .log().body()
                .statusCode(200)
                .body("username", equalTo("updateduser"))
                .body("email", equalTo("updateduser@gmail.com"));
    }

    @Test
    public void deleteUserTest() {

        given()
                .when()
                .delete("/users/1")
                .then()
                .log().body()
                .statusCode(200);
    }
}