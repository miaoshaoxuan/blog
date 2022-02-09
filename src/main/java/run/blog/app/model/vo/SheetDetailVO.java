package run.blog.app.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import run.blog.app.model.dto.BaseMetaDTO;
import run.blog.app.model.dto.post.BasePostDetailDTO;

import java.util.List;
import java.util.Set;

/**
 * Sheet detail VO.
 *
 * @author ryanwang
 * @date 2019-12-10
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SheetDetailVO extends BasePostDetailDTO {

    private Set<Long> metaIds;

    private List<BaseMetaDTO> metas;
}
