package utils;

import io.restassured.response.Response;

import java.util.List;

public class UserIdCollector {

    public static List<String> collectIds(Response response) {
        return response.jsonPath().getList("id", String.class);
    }
}
