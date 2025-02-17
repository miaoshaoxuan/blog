package run.blog.app.model.enums;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Data type test.
 *
 * @author johnniang
 * @date 19-4-21
 */
@Slf4j
class DataTypeTest {

    @Test
    void typeOf() {
        DataType type = DataType.typeOf("bool");
        assertEquals(DataType.BOOL, type);
    }
}