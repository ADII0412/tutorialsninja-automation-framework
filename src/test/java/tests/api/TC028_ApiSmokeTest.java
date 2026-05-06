package tests.api;

import base.BaseAPI;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;

public class TC028_ApiSmokeTest extends BaseAPI {

    @Test(description = "Verify posts list API returns posts")
    public void verifyGetPostsReturnsNonEmptyList() {
        given()
                .spec(requestSpec)
        .when()
                .get("/posts")
        .then()
                .statusCode(200)
                .body("size()", not(equalTo(0)));
    }

    @Test(description = "Verify single post API contains userId")
    public void verifyGetPostByIdContainsUserId() {
        given()
                .spec(requestSpec)
        .when()
                .get("/posts/1")
        .then()
                .statusCode(200)
                .body("$", hasKey("userId"));
    }

    @Test(description = "Verify create post API returns created post")
    public void verifyCreatePostReturnsSentTitle() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "automation smoke title");
        payload.put("body", "automation smoke body");
        payload.put("userId", 1);

        given()
                .spec(requestSpec)
                .body(payload)
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .body("title", equalTo(payload.get("title")));
    }

    @Test(description = "Verify missing post returns 404")
    public void verifyMissingPostReturnsNotFound() {
        given()
                .spec(requestSpec)
        .when()
                .get("/posts/999")
        .then()
                .statusCode(404);
    }

    @Test(description = "Verify user API returns non-empty email")
    public void verifyGetUserByIdContainsEmail() {
        given()
                .spec(requestSpec)
        .when()
                .get("/users/1")
        .then()
                .statusCode(200)
                .body("email", not(emptyString()));
    }
}
