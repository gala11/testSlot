package tests;

import config.RequestSpecFactory;
import config.ResponseSpecFactory;
import config.TokenStorage;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;

import static io.restassured.RestAssured.given;

public class AutomationTaskCreateTest {

    @Test
    void create_automation_task() {

        String token = TokenStorage.read();

        given()
                .spec(RequestSpecFactory.authorizedSpec(token))
                .body(JsonUtils.resolve("/automationTaskCreate/request").toString())
                .when()
                .post("/automationTask/create")
                .then()
                .spec(ResponseSpecFactory.success201());
    }
}
