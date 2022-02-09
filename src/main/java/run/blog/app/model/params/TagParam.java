package run.blog.app.model.params;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import run.blog.app.utils.HaloUtils;
import run.blog.app.utils.SlugUtils;
import run.blog.app.model.dto.base.InputConverter;
import run.blog.app.model.entity.Tag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Tag param.
 *
 * @author johnniang
 * @date 3/20/19
 */
@Data
public class TagParam implements InputConverter<Tag> {

    @NotBlank(message = "标签名称不能为空")
    @Size(max = 255, message = "标签名称的字符长度不能超过 {max}")
    private String name;

    @Size(max = 255, message = "标签别名的字符长度不能超过 {max}")
    private String slugName;

    @Override
    public Tag convertTo() {
        if (StringUtils.isBlank(slugName)) {
            // Handle slug name
            slugName = SlugUtils.slugify(name);
        }

        slugName = HaloUtils.initializeUrlIfBlank(slugName);

        return InputConverter.super.convertTo();
    }
}
