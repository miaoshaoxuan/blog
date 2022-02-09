package run.blog.app.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import run.blog.app.model.dto.BaseMetaDTO;
import run.blog.app.model.dto.CategoryDTO;
import run.blog.app.model.dto.TagDTO;
import run.blog.app.model.dto.post.BasePostDetailDTO;

import java.util.List;
import java.util.Set;

/**
 * Post vo.
 *
 * @author johnniang
 * @author guqing
 * @date 2019-03-21
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostDetailVO extends BasePostDetailDTO {

    private Set<Integer> tagIds;

    private List<TagDTO> tags;

    private Set<Integer> categoryIds;

    private List<CategoryDTO> categories;

    private Set<Long> metaIds;

    private List<BaseMetaDTO> metas;
}

