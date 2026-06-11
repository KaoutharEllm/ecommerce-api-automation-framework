package tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @Test
    public void getAllProductsTest() {
        given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void getSingleProductTest() {
        given()
                .when()
                .get("/products/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("price", greaterThan(0f))
                .body("category", notNullValue())
                .body("description", notNullValue())
                .body("image", containsString("http"));
    }

    @Test
    public void addNewProductTest() {
        String requestBody = """
                {
                  "title": "Test Product",
                  "price": 29.99,
                  "description": "Product created for API automation testing",
                  "image": "https://example.com/product.png",
                  "category": "electronics"
                }
                """;

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("Test Product"))
                .body("price", equalTo(29.99f));
    }

    @Test
    public void updateProductTest() {
        String requestBody = """
                {
                  "title": "Updated Product",
                  "price": 49.99,
                  "description": "Product updated for API automation testing",
                  "image": "https://example.com/updated-product.png",
                  "category": "electronics"
                }
                """;

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/products/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Product"))
                .body("price", equalTo(49.99f));
    }

    @Test
    public void deleteProductTest() {
        given()
                .when()
                .delete("/products/1")
                .then()
                .statusCode(200);
    }
}