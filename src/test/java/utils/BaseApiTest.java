package utils;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

public abstract class BaseApiTest {

    @BeforeAll
    static void setup() {
        enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
