package run.blog.app.model.dto;

import lombok.Data;
import run.blog.app.model.entity.Link;
import run.blog.app.model.dto.base.OutputConverter;

/**
 * Link output dto.
 *
 * @author ryanwang
 * @date : 2019/3/21
 */
@Data
public class LinkDTO implements OutputConverter<LinkDTO, Link> {

    private Integer id;

    private String name;

    private String url;

    private String logo;

    private String description;

    private String team;
}
