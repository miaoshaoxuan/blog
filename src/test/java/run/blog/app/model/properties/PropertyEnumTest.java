package run.blog.app.model.properties;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author johnniang
 * @date 19-4-25
 */
@Slf4j
class PropertyEnumTest {

    @Test
    void getValuePropertyMapTest() {
        Map<String, PropertyEnum> result = PropertyEnum.getValuePropertyEnumMap();

        log.debug(result.toString());
    }
}