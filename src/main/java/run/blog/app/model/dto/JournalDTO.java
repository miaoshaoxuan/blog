package run.blog.app.model.dto;

import lombok.Data;
import run.blog.app.model.entity.Journal;
import run.blog.app.model.dto.base.OutputConverter;

import java.util.Date;

/**
 * Journal dto.
 *
 * @author johnniang
 * @date 19-4-24
 */
@Data
public class JournalDTO implements OutputConverter<JournalDTO, Journal> {

    private Integer id;

    private String content;

    private Long likes;

    private Date createTime;
}
