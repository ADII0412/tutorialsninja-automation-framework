package tests.api;

import base.BaseAPI;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;

public class TC028_ApiSmokeTest extends BaseAPI {

    @Test(groups = {"regression", "smoke", "sanity", "api"}, description = "Verify valid login returns token")
    public void verifyValidLoginReturnsToken() {
        logger.info("Testing POST /login with valid credentials");
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "eve.holt@reqres.in");
        payload.put("password", "cityslicka");

        given()
                .spec(requestSpec)
                .body(payload)
        .when()
                .post("/login")
        .then()
                .statusCode(200)
                .body("$", hasKey("token"))
                .body("token", not(emptyString()));
    }

    @Test(groups = {"regression", "smoke", "sanity", "api"}, description = "Verify invalid login returns error")
    public void verifyInvalidLoginReturnsError() {
        logger.info("Testing POST /login with invalid credentials");
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "invalid@test.com");
        payload.put("password", "wrongpass");

        given()
                .spec(requestSpec)
                .body(payload)
        .when()
                .post("/login")
        .then()
                .statusCode(400)
                .body("$", hasKey("error"))
                .body("error", not(emptyString()));
    }

    @Test(groups = {"regression", "smoke", "sanity", "api"}, description = "Verify valid registration returns id and token")
    public void verifyValidRegistrationReturnsIdAndToken() {
        logger.info("Testing POST /register with valid registration data");
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "eve.holt@reqres.in");
        payload.put("password", "pistol");

        given()
                .spec(requestSpec)
                .body(payload)
        .when()
                .post("/register")
        .then()
                .statusCode(200)
                .body("$", hasKey("id"))
                .body("$", hasKey("token"));
    }

    @Test(groups = {"regression", "smoke", "sanity", "api"}, description = "Verify invalid registration returns missing password error")
    public void verifyInvalidRegistrationReturnsMissingPasswordError() {
        logger.info("Testing POST /register without password");
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "invalidemail@unknown.com");

        given()
                .spec(requestSpec)
                .body(payload)
        .when()
                .post("/register")
        .then()
                .statusCode(400)
                .body("$", hasKey("error"))
                .body("error", containsString("Missing password"));
    }

    @Test(groups = {"regression", "smoke", "sanity", "api"}, description = "Verify get user returns user details")
    public void verifyGetUserReturnsUserDetails() {
        logger.info("Testing GET /users/2");
        given()
                .spec(requestSpec)
        .when()
                .get("/users/2")
        .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", not(emptyString()))
                .body("data.first_name", not(emptyString()));
    }

    @Test(groups = {"regression", "smoke", "sanity", "api"}, description = "Verify non-existing user returns 404")
    public void verifyGetNonExistingUserReturnsNotFound() {
        logger.info("Testing GET /users/23");
        given()
                .spec(requestSpec)
        .when()
                .get("/users/23")
        .then()
                .statusCode(404)
                .body(anyOf(equalTo(""), equalTo("{}")));
    }
}
