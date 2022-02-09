package run.blog.app.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import run.blog.app.model.dto.base.OutputConverter;
import run.blog.app.model.entity.BaseComment;
import run.blog.app.model.enums.CommentStatus;

import java.util.Date;

/**
 * Base comment output dto.
 *
 * @author johnniang
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseCommentDTO implements OutputConverter<BaseCommentDTO, BaseComment> {

    private Long id;

    private String author;

    private String email;

    private String ipAddress;

    private String authorUrl;

    private String gravatarMd5;

    private String content;

    private CommentStatus status;

    private String userAgent;

    private Long parentId;

    private Boolean isAdmin;

    private Date createTime;

}
