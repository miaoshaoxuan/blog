package run.blog.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import run.blog.app.model.entity.JournalComment;
import run.blog.app.model.projection.CommentChildrenCountProjection;
import run.blog.app.model.projection.CommentCountProjection;
import run.blog.app.repository.base.BaseCommentRepository;

import java.util.Collection;
import java.util.List;

/**
 * Journal comment repository.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-24
 */
public interface JournalCommentRepository extends BaseCommentRepository<JournalComment> {

    /**
     * Count the number of comments by post id.
     *
     * @param postIds post id collection must not be null
     * @return a list of CommentCountProjection
     */
    @Query("select new run.blog.app.model.projection.CommentCountProjection(count(comment.id), comment.postId) " +
            "from JournalComment comment " +
            "where comment.postId in ?1 group by comment.postId")
    @NonNull
    @Override
    List<CommentCountProjection> countByPostIds(@NonNull Collection<Integer> postIds);

    /**
     * Finds direct children count by comment ids.
     *
     * @param commentIds comment ids must not be null.
     * @return a list of CommentChildrenCountProjection
     */
    @Query("select new run.blog.app.model.projection.CommentChildrenCountProjection(count(comment.id), comment.parentId) " +
            "from JournalComment comment " +
            "where comment.parentId in ?1 " +
            "group by comment.parentId")
    @NonNull
    @Override
    List<CommentChildrenCountProjection> findDirectChildrenCount(@NonNull Collection<Long> commentIds);
}
