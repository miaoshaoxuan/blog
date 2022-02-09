package run.blog.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import run.blog.app.repository.base.BaseCommentRepository;
import run.blog.app.model.entity.SheetComment;
import run.blog.app.model.projection.CommentChildrenCountProjection;
import run.blog.app.model.projection.CommentCountProjection;

import java.util.List;

/**
 * Sheet comment repository.
 *
 * @author johnniang
 * @date 19-4-24
 */
public interface SheetCommentRepository extends BaseCommentRepository<SheetComment> {

    @Query("select new run.blog.app.model.projection.CommentCountProjection(count(comment.id), comment.postId) " +
            "from SheetComment comment " +
            "where comment.postId in ?1 group by comment.postId")
    @NonNull
    @Override
    List<CommentCountProjection> countByPostIds(@NonNull Iterable<Integer> postIds);

    @Query("select new run.blog.app.model.projection.CommentChildrenCountProjection(count(comment.id), comment.parentId) " +
            "from SheetComment comment " +
            "where comment.parentId in ?1 " +
            "group by comment.parentId")
    @NonNull
    List<CommentChildrenCountProjection> findDirectChildrenCount(@NonNull Iterable<Long> commentIds);
}
