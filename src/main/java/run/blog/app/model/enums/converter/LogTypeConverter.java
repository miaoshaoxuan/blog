package run.blog.app.model.enums.converter;

import run.blog.app.model.enums.LogType;

import javax.persistence.Converter;

/**
 * Log type converter.
 *
 * @author johnniang
 * @date 3/27/19
 */
@Converter(autoApply = true)
public class LogTypeConverter extends AbstractConverter<LogType, Integer> {

    public LogTypeConverter() {
        super(LogType.class);
    }
}
