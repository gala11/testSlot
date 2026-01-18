package tests;

import config.RequestSpecFactory;
import config.ResponseSpecFactory;
import config.TokenStorage;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import utils.BaseApiTest;
import utils.UserIdCollector;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutomationTaskDeleteAllTest extends BaseApiTest {

    @Test
    void delete_all_users() {

        String token = TokenStorage.read();

        // 1. Получаем всех пользователей
        Response getAllResponse =
                given()
                        .spec(RequestSpecFactory.authorizedSpec(token))
                        .when()
                        .get("/automationTask/getAll")
                        .then()
                        .spec(ResponseSpecFactory.success200())
                        .extract()
                        .response();

        List<String> ids = UserIdCollector.collectIds(getAllResponse);

        if (ids.isEmpty()) {
            return;
        }

        for (String id : ids) {

            given()
                    .spec(RequestSpecFactory.authorizedSpec(token))
                    .when()
                    .delete("/automationTask/deleteOne/{id}", id)
                    .then()
                    .spec(ResponseSpecFactory.success200());
        }

        Response afterDeleteResponse =
                given()
                        .spec(RequestSpecFactory.authorizedSpec(token))
                        .when()
                        .get("/automationTask/getAll")
                        .then()
                        .spec(ResponseSpecFactory.success200())
                        .extract()
                        .response();

        List<String> remainingIds =
                afterDeleteResponse.jsonPath().getList("id");

        assertTrue(
                remainingIds == null || remainingIds.isEmpty(),
                "Users list is not empty after delete"
        );
    }
}
