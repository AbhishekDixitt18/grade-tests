import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GradeApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    // Test 1 - GET /posts
    @Test
    public void testGetPosts() {

        Response response =
                given()
                        .when()
                        .get("/posts")
                        .then()
                        .statusCode(200)
                        .body("$", not(empty()))
                        .extract().response();

        System.out.println("GET /posts test passed");
    }

    // Test 2 - GET /posts/1
    @Test
    public void testGetPostById() {

        Response response =
                given()
                        .when()
                        .get("/posts/1")
                        .then()
                        .statusCode(200)
                        .body("id", equalTo(1))
                        .body("title", not(emptyOrNullString()))
                        .extract().response();

        System.out.println("GET /posts/1 test passed");
    }

    // Test 3 - POST /posts
    @Test
    public void testCreatePost() {

        String requestBody =
                "{\n" +
                        "  \"title\": \"API Testing\",\n" +
                        "  \"body\": \"REST Assured POST Request\",\n" +
                        "  \"userId\": 1\n" +
                        "}";

        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/posts")
                        .then()
                        .statusCode(201)
                        .body("title", equalTo("API Testing"))
                        .extract().response();

        System.out.println("POST /posts test passed");
    }

    // Negative Test 4 - GET invalid post
    @Test
    public void testGetInvalidPost() {

        given()
                .when()
                .get("/posts/99999")
                .then()
                .statusCode(404);

        System.out.println("GET invalid post test passed");
    }

    // Negative Test 5 - DELETE post
    @Test
    public void testDeletePost() {

        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);

        System.out.println("DELETE /posts/1 test passed");
    }
}