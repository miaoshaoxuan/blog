package run.blog.app.model.enums.converter;

import run.blog.app.model.enums.PostType;

import javax.persistence.Converter;

/**
 * PostType converter.
 *
 * @author johnniang
 * @date 3/27/19
 */
@Converter(autoApply = true)
@Deprecated
public class PostTypeConverter extends AbstractConverter<PostType, Integer> {

}
