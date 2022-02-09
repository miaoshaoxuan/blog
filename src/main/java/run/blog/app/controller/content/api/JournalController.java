package run.blog.app.controller.content.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import run.blog.app.cache.lock.CacheLock;
import run.blog.app.model.dto.BaseCommentDTO;
import run.blog.app.model.entity.JournalComment;
import run.blog.app.model.enums.CommentStatus;
import run.blog.app.model.params.JournalCommentParam;
import run.blog.app.model.vo.BaseCommentVO;
import run.blog.app.model.vo.BaseCommentWithParentVO;
import run.blog.app.model.vo.CommentWithHasChildrenVO;
import run.blog.app.service.JournalCommentService;
import run.blog.app.service.JournalService;
import run.blog.app.service.OptionService;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author johnniang
 * @date 19-4-26
 */
@RestController("PortalJournalController")
@RequestMapping("/api/content/journals")
public class JournalController {

    private final JournalService journalService;

    private final JournalCommentService journalCommentService;

    private final OptionService optionService;

    public JournalController(JournalService journalService,
                             JournalCommentService journalCommentService,
                             OptionService optionService) {
        this.journalService = journalService;
        this.journalCommentService = journalCommentService;
        this.optionService = optionService;
    }

    @GetMapping("{journalId:\\d+}/comments/top_view")
    public Page<CommentWithHasChildrenVO> listTopComments(@PathVariable("journalId") Integer journalId,
                                                          @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                          @SortDefault(sort = "createTime", direction = DESC) Sort sort) {
        Page<CommentWithHasChildrenVO> result = journalCommentService.pageTopCommentsBy(journalId, CommentStatus.PUBLISHED, PageRequest.of(page, optionService.getCommentPageSize(), sort));
        return journalCommentService.filterIpAddress(result);
    }

    @GetMapping("{journalId:\\d+}/comments/{commentParentId:\\d+}/children")
    public List<BaseCommentDTO> listChildrenBy(@PathVariable("journalId") Integer journalId,
                                               @PathVariable("commentParentId") Long commentParentId,
                                               @SortDefault(sort = "createTime", direction = DESC) Sort sort) {
        // Find all children comments
        List<JournalComment> postComments = journalCommentService.listChildrenBy(journalId, commentParentId, CommentStatus.PUBLISHED, sort);
        // Convert to base comment dto
        List<BaseCommentDTO> result = journalCommentService.convertTo(postComments);
        return journalCommentService.filterIpAddress(result);
    }

    @GetMapping("{journalId:\\d+}/comments/tree_view")
    @ApiOperation("Lists comments with tree view")
    public Page<BaseCommentVO> listCommentsTree(@PathVariable("journalId") Integer journalId,
                                                @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                @SortDefault(sort = "createTime", direction = DESC) Sort sort) {
        Page<BaseCommentVO> result = journalCommentService.pageVosBy(journalId, PageRequest.of(page, optionService.getCommentPageSize(), sort));
        return journalCommentService.filterIpAddress(result);
    }

    @GetMapping("{journalId:\\d+}/comments/list_view")
    @ApiOperation("Lists comment with list view")
    public Page<BaseCommentWithParentVO> listComments(@PathVariable("journalId") Integer journalId,
                                                      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                      @SortDefault(sort = "createTime", direction = DESC) Sort sort) {
        Page<BaseCommentWithParentVO> result = journalCommentService.pageWithParentVoBy(journalId, PageRequest.of(page, optionService.getCommentPageSize(), sort));
        return journalCommentService.filterIpAddress(result);
    }

    @PostMapping("comments")
    @ApiOperation("Comments a post")
    @CacheLock(autoDelete = false, traceRequest = true)
    public BaseCommentDTO comment(@RequestBody JournalCommentParam journalCommentParam) {
        return journalCommentService.convertTo(journalCommentService.createBy(journalCommentParam));
    }
}
