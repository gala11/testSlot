package tests;

import config.RequestSpecFactory;
import config.ResponseSpecFactory;
import config.TokenStorage;
import org.junit.jupiter.api.Test;
import utils.BaseApiTest;
import utils.JsonUtils;

import static io.restassured.RestAssured.given;

public class LoginTest extends BaseApiTest {

    @Test
    void login_and_save_token() {

        String token =
                given()
                        .spec(RequestSpecFactory.baseSpec())
                        .body(JsonUtils.resolve("/login/request").toString())
                        .when()
                        .post("/tester/login")
                        .then()
                        .spec(ResponseSpecFactory.success201())
                        .extract()
                        .path("accessToken");

        TokenStorage.save(token);
    }
}
