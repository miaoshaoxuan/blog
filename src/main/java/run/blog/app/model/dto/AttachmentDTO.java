package run.blog.app.model.dto;

import lombok.Data;
import run.blog.app.model.entity.Attachment;
import run.blog.app.model.enums.AttachmentType;
import run.blog.app.model.dto.base.OutputConverter;

import java.util.Date;

/**
 * Attachment output dto.
 *
 * @author johnniang
 * @date 3/21/19
 */
@Data
public class AttachmentDTO implements OutputConverter<AttachmentDTO, Attachment> {

    private Integer id;

    private String name;

    private String path;

    private String fileKey;

    private String thumbPath;

    private String mediaType;

    private String suffix;

    private Integer width;

    private Integer height;

    private Long size;

    private AttachmentType type;

    private Date createTime;
}
