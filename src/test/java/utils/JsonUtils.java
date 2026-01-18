package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final File DATA_FILE =
            new File("src/test/resources/data/test-data.json");

    public static JsonNode resolve(String testName) {
        try {
            JsonNode root = MAPPER.readTree(DATA_FILE);
            JsonNode defaults = root.path("defaults");

            ObjectNode request =
                    (ObjectNode) root
                            .path(testName)
                            .path("request")
                            .deepCopy();

            applyDefaults(request, defaults);

            return request;
        } catch (IOException e) {
            throw new RuntimeException("Cannot resolve test data", e);
        }
    }

    private static void applyDefaults(ObjectNode request, JsonNode defaults) {
        Iterator<Map.Entry<String, JsonNode>> fields = request.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();

            if (field.getValue().isTextual()
                    && field.getValue().asText().isEmpty()
                    && defaults.has(field.getKey())) {

                request.put(
                        field.getKey(),
                        defaults.get(field.getKey()).asText()
                );
            }
        }
    }
}
