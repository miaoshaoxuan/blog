package run.blog.app.service;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.blog.app.model.entity.JournalComment;
import run.blog.app.model.vo.JournalCommentWithJournalVO;
import run.blog.app.service.base.BaseCommentService;

import java.util.List;

/**
 * Journal comment service interface.
 *
 * @author johnniang
 * @date 2019-04-25
 */
public interface JournalCommentService extends BaseCommentService<JournalComment> {

    @NonNull
    List<JournalCommentWithJournalVO> convertToWithJournalVo(@Nullable List<JournalComment> journalComments);

    @NonNull
    Page<JournalCommentWithJournalVO> convertToWithJournalVo(@NonNull Page<JournalComment> journalCommentPage);
}
