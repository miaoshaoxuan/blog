package run.blog.app.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.blog.app.model.dto.post.BasePostDetailDTO;

import java.util.Set;

/**
 * Post vo.
 *
 * @author johnniang
 * @date 3/21/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostDetailVO extends BasePostDetailDTO {

    private Set<Integer> tagIds;

    private Set<Integer> categoryIds;
}
