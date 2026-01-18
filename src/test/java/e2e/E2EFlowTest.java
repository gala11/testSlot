package e2e;

import com.fasterxml.jackson.databind.node.ObjectNode;
import config.RequestSpecFactory;
import config.ResponseSpecFactory;
import config.TokenStorage;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import utils.BaseApiTest;
import utils.JsonUtils;
import utils.TestDataGenerator;
import utils.UserIdCollector;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2EFlowTest extends BaseApiTest {

    @Test
    void full_e2e_flow() {

        // 1. LOGIN
        String token =
                given()
                        .spec(RequestSpecFactory.baseSpec())
                        .body(JsonUtils.resolve("login").toString())
                        .when()
                        .post("/tester/login")
                        .then()
                        .spec(ResponseSpecFactory.success201())
                        .extract()
                        .path("accessToken");

        TokenStorage.save(token);

        // 2. CREATE
        for (int i = 1; i <= 12; i++) {

            ObjectNode createRequest =
                    (ObjectNode) JsonUtils.resolve("automationTaskCreate");

            createRequest.put("email", TestDataGenerator.uniqueEmail(i));
            createRequest.put("username", TestDataGenerator.uniqueUsername(i));

            String createdEmail =
                    given()
                            .spec(RequestSpecFactory.authorizedSpec(token))
                            .body(createRequest.toString())
                            .when()
                            .post("/automationTask/create")
                            .then()
                            .spec(ResponseSpecFactory.success201())
                            .extract()
                            .path("email");

            ObjectNode getOneRequest =
                    (ObjectNode) JsonUtils.resolve("automationTaskGetOne");

            getOneRequest.put("email", createdEmail);

            given()
                    .spec(RequestSpecFactory.authorizedSpec(token))
                    .body(getOneRequest.toString())
                    .when()
                    .post("/automationTask/getOne")
                    .then()
                    .spec(ResponseSpecFactory.success201());


            // 4. GET ALL
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

            // 5. DELETE ALL
            for (String id : ids) {
                given()
                        .spec(RequestSpecFactory.authorizedSpec(token))
                        .when()
                        .delete("/automationTask/deleteOne/{id}", id)
                        .then()
                        .spec(ResponseSpecFactory.success200());
            }

            // 6. GET ALL → EMPTY
            Response afterDelete =
                    given()
                            .spec(RequestSpecFactory.authorizedSpec(token))
                            .when()
                            .get("/automationTask/getAll")
                            .then()
                            .spec(ResponseSpecFactory.success200())
                            .extract()
                            .response();

            List<String> remainingIds =
                    afterDelete.jsonPath().getList("id");

            assertTrue(
                    remainingIds == null || remainingIds.isEmpty(),
                    "Users list is not empty after delete"
            );
        }
    }
}
