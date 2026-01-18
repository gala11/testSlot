package config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TokenStorage {

    private static final File TOKEN_FILE =
            new File("src/test/resources/token/access-token.json");

    public static void save(String token) {
        try {
            new ObjectMapper().writeValue(TOKEN_FILE, Map.of("accessToken", token));
        } catch (IOException e) {
            throw new RuntimeException("Cannot save access token", e);
        }
    }

    public static String read() {
        try {
            Map<?, ?> map = new ObjectMapper().readValue(TOKEN_FILE, Map.class);
            return map.get("accessToken").toString();
        } catch (IOException e) {
            throw new RuntimeException("Cannot read access token", e);
        }
    }
}
