package tests;

import config.RequestSpecFactory;
import config.ResponseSpecFactory;
import config.TokenStorage;
import org.junit.jupiter.api.Test;
import utils.BaseApiTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AutomationTaskGetAllTest extends BaseApiTest {

    @Test
    void get_all_users() {

        String token = TokenStorage.read();

        given()
                .spec(RequestSpecFactory.authorizedSpec(token))
                .when()
                .get("/automationTask/getAll")
                .then()
                .spec(ResponseSpecFactory.success200())
                .body("$", notNullValue())
                .body("$", instanceOf(java.util.List.class));
    }
}
