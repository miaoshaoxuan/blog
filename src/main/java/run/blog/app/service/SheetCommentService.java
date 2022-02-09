package run.blog.app.service;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.blog.app.service.base.BaseCommentService;
import run.blog.app.model.entity.SheetComment;
import run.blog.app.model.vo.SheetCommentWithSheetVO;

import java.util.List;

/**
 * Sheet comment service interface.
 *
 * @author johnniang
 * @date 19-4-24
 */
public interface SheetCommentService extends BaseCommentService<SheetComment> {

    @NonNull
    List<SheetCommentWithSheetVO> convertToWithPostVo(@Nullable List<SheetComment> sheetComments);

    @NonNull
    Page<SheetCommentWithSheetVO> convertToWithPostVo(@NonNull Page<SheetComment> sheetCommentPage);
}
