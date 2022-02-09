package run.blog.app.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import run.blog.app.model.dto.base.OutputConverter;
import run.blog.app.model.entity.BaseMeta;

import java.util.Date;

/**
 * Base meta Dto.
 *
 * @author ryanwang
 * @date 2019-12-10
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseMetaDTO implements OutputConverter<BaseMetaDTO, BaseMeta> {
    private Long id;

    private Integer postId;

    private String key;

    private String value;

    private Date createTime;
}
