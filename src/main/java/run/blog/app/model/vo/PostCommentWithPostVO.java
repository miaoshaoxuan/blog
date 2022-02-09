package run.blog.app.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import run.blog.app.model.dto.BaseCommentDTO;
import run.blog.app.model.dto.post.BasePostMinimalDTO;

/**
 * PostComment list with post vo.
 *
 * @author johnniang
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class PostCommentWithPostVO extends BaseCommentDTO {

    private BasePostMinimalDTO post;
}
