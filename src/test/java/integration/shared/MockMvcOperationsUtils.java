package integration.shared;

import lombok.SneakyThrows;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Contains various helper methods that help reduce amount of copy-paste
 */
public class MockMvcOperationsUtils {

    public static void assertJsonPaths(
            ResultActions resultActions,
            Map<String, Object> expectedValues
    ) {
        try {
            for (Map.Entry<String, Object> entry : expectedValues.entrySet()) {
                resultActions.andExpect(jsonPath(entry.getKey()).value(entry.getValue()));
            }
        } catch (Exception exception) {
            throw new AssertionError("JSON path assertion failed", exception);
        }
    }
}
