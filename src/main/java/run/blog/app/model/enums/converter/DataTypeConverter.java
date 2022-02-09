package run.blog.app.model.enums.converter;

import run.blog.app.model.enums.DataType;

import javax.persistence.Converter;

/**
 * Data type converter.
 *
 * @author johnniang
 * @date 4/10/19
 */
@Converter(autoApply = true)
public class DataTypeConverter extends AbstractConverter<DataType, Integer> {

    public DataTypeConverter() {
        super(DataType.class);
    }
}
