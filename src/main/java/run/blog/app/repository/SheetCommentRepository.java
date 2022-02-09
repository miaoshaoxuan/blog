package run.blog.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import run.blog.app.model.entity.SheetComment;
import run.blog.app.model.projection.CommentChildrenCountProjection;
import run.blog.app.model.projection.CommentCountProjection;
import run.blog.app.repository.base.BaseCommentRepository;

import java.util.Collection;
import java.util.List;

/**
 * Sheet comment repository.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-24
 */
public interface SheetCommentRepository extends BaseCommentRepository<SheetComment> {

    /**
     * Count comments by sheet ids.
     *
     * @param sheetIds sheet id collection must not be null
     * @return a list of CommentCountProjection
     */
    @Query("select new run.blog.app.model.projection.CommentCountProjection(count(comment.id), comment.postId) " +
            "from SheetComment comment " +
            "where comment.postId in ?1 group by comment.postId")
    @NonNull
    @Override
    List<CommentCountProjection> countByPostIds(@NonNull Collection<Integer> sheetIds);

    /**
     * Finds direct children count by comment ids.
     *
     * @param commentIds comment ids must not be null.
     * @return a list of CommentChildrenCountProjection
     */
    @Query("select new run.blog.app.model.projection.CommentChildrenCountProjection(count(comment.id), comment.parentId) " +
            "from SheetComment comment " +
            "where comment.parentId in ?1 " +
            "group by comment.parentId")
    @NonNull
    @Override
    List<CommentChildrenCountProjection> findDirectChildrenCount(@NonNull Collection<Long> commentIds);
}
