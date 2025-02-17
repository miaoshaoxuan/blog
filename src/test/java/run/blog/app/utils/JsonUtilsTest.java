package run.blog.app.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author johnniang
 * @date 19-4-29
 */
class JsonUtilsTest {

    @Test
    void longConvertTest() throws IOException {
        long num = 10;

        String result = JsonUtils.objectToJson(num);

        assertEquals("10", result);

        num = JsonUtils.jsonToObject("10", Long.class);

        assertEquals(10, num);
    }
}