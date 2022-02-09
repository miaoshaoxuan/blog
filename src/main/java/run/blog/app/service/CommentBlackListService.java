package run.blog.app.service;

import run.blog.app.model.entity.CommentBlackList;
import run.blog.app.model.enums.CommentViolationTypeEnum;
import run.blog.app.service.base.CrudService;

/**
 * Comment BlackList Service
 *
 * @author Lei XinXin
 * @date 2020/1/3
 */
public interface CommentBlackListService extends CrudService<CommentBlackList, Long> {
    /**
     * 评论封禁状态
     *
     * @param ipAddress ip地址
     * @return boolean
     */
    CommentViolationTypeEnum commentsBanStatus(String ipAddress);
}
