package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class RequestSpecFactory {

    public static RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ApiConfig.BASE_URL)
                .setBasePath(ApiConfig.BASE_PATH)
                .setContentType(JSON)
                .addFilter(new ApiErrorLoggingFilter())
                .build();
    }

    public static RequestSpecification authorizedSpec(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(baseSpec())
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
