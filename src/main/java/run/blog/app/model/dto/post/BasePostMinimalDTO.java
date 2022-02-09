package run.blog.app.model.dto.post;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import run.blog.app.model.dto.base.OutputConverter;
import run.blog.app.model.entity.BasePost;
import run.blog.app.model.enums.PostEditorType;
import run.blog.app.model.enums.PostStatus;

import java.util.Date;

/**
 * Base post minimal output dto.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-19
 */
@Data
@ToString
@EqualsAndHashCode
public class BasePostMinimalDTO implements OutputConverter<BasePostMinimalDTO, BasePost> {

    private Integer id;

    private String title;

    private PostStatus status;

    private String slug;

    private PostEditorType editorType;

    private Date updateTime;

    private Date createTime;

    private Date editTime;

    private String metaKeywords;

    private String metaDescription;

    private String fullPath;
}
