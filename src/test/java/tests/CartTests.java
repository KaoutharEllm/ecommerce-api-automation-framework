package tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CartTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @Test
    public void getAllCartsTest() {
        given()
                .when()
                .get("/carts")
                .then()
                .log().body()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void getSingleCartTest() {
        given()
                .when()
                .get("/carts/1")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("products", notNullValue());
    }

    @Test
    public void addNewCartTest() {
        String requestBody = """
                {
                  "userId": 1,
                  "products": [
                    {
                      "productId": 1,
                      "quantity": 2
                    }
                  ]
                }
                """;

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/carts")
                .then()
                .log().body()
                .statusCode(201)
                .body("id", notNullValue())
                .body("userId", equalTo(1))
                .body("products", notNullValue());
    }

    @Test
    public void updateCartTest() {
        String requestBody = """
                {
                  "userId": 1,
                  "products": [
                    {
                      "productId": 1,
                      "quantity": 5
                    }
                  ]
                }
                """;

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/carts/1")
                .then()
                .log().body()
                .statusCode(200)
                .body("userId", equalTo(1))
                .body("products", notNullValue());
    }

    @Test
    public void deleteCartTest() {
        given()
                .when()
                .delete("/carts/1")
                .then()
                .log().body()
                .statusCode(200);
    }
}