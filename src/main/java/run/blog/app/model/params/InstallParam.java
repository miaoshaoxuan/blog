package run.blog.app.model.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.blog.app.model.support.CreateCheck;

import javax.validation.constraints.NotBlank;

/**
 * Install parameters.
 *
 * @author johnniang
 * @date 3/19/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InstallParam extends UserParam {

    /**
     * Blog locale.
     */
    private String locale = "zh";

    /**
     * Blog title.
     */
    @NotBlank(message = "博客名称不能为空", groups = CreateCheck.class)
    private String title;

    /**
     * Blog url.
     */
    private String url;

}
