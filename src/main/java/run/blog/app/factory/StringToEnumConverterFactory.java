package run.blog.app.factory;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author ryanwang
 * @date 2019-3-14
 */
@Component
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    @Override
    @NonNull
    public <T extends Enum<?>> Converter<String, T> getConverter(@NotNull Class<T> targetType) {
        return new StringToEnumConverter(targetType);
    }

    private static class StringToEnumConverter<T extends Enum<T>>
            implements Converter<String, T> {

        private final Class<T> enumType;

        private StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            return Enum.valueOf(this.enumType, source.toUpperCase());
        }
    }
}
