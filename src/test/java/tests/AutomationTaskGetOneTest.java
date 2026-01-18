package tests;

import config.RequestSpecFactory;
import config.ResponseSpecFactory;
import config.TokenStorage;
import org.junit.jupiter.api.Test;
import utils.BaseApiTest;
import utils.JsonUtils;


import static io.restassured.RestAssured.given;

public class AutomationTaskGetOneTest extends BaseApiTest {

    @Test
    void get_one_user() {

        String token = TokenStorage.read();

        given()
                .spec(RequestSpecFactory.authorizedSpec(token))
                .body(JsonUtils
                        .resolve("automationTaskGetOne")
                        .toString())
                .when()
                .post("/automationTask/getOne")
                .then()
                .spec(ResponseSpecFactory.success201());
    }
}
