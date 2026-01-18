package utils;

import java.time.Instant;

public class TestDataGenerator {

    public static String uniqueEmail(int index) {
        return "test_user_" + index + "_" + Instant.now().toEpochMilli() + "@test.com";
    }

    public static String uniqueUsername(int index) {
        return "user_" + index + "_" + Instant.now().toEpochMilli();
    }
}
