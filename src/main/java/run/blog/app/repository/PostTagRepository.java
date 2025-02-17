package run.blog.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import run.blog.app.model.entity.PostTag;
import run.blog.app.model.enums.PostStatus;
import run.blog.app.model.projection.TagPostPostCountProjection;
import run.blog.app.repository.base.BaseRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * Post tag repository.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-19
 */
public interface PostTagRepository extends BaseRepository<PostTag, Integer> {

    /**
     * Finds all post tags by post id.
     *
     * @param postId post id must not be null
     * @return a list of post tags
     */
    @NonNull
    List<PostTag> findAllByPostId(@NonNull Integer postId);

    /**
     * Finds all tag ids by post id.
     *
     * @param postId post id must not be null
     * @return a set of tag id
     */
    @Query("select postTag.tagId from PostTag postTag where postTag.postId = ?1")
    @NonNull
    Set<Integer> findAllTagIdsByPostId(@NonNull Integer postId);

    /**
     * Finds all post tags by tag id.
     *
     * @param tagId tag id must not be null
     * @return a list of post tags
     */
    @NonNull
    List<PostTag> findAllByTagId(@NonNull Integer tagId);

    /**
     * Finds all post id by tag id.
     *
     * @param tagId tag id must not be null
     * @return a set of post id
     */
    @Query("select postTag.postId from PostTag postTag where postTag.tagId = ?1")
    @NonNull
    Set<Integer> findAllPostIdsByTagId(@NonNull Integer tagId);

    /**
     * Finds all post id by tag id and post status.
     *
     * @param tagId  tag id must not be null
     * @param status post status
     * @return a set of post id
     */
    @Query("select postTag.postId from PostTag postTag,Post post where postTag.tagId = ?1 and post.id = postTag.postId and post.status = ?2")
    @NonNull
    Set<Integer> findAllPostIdsByTagId(@NonNull Integer tagId, @NonNull PostStatus status);

    /**
     * Finds all tags by post id in.
     *
     * @param postIds post id collection
     * @return a list of post tags
     */
    @NonNull
    List<PostTag> findAllByPostIdIn(@NonNull Collection<Integer> postIds);

    /**
     * Deletes post tags by post id.
     *
     * @param postId post id must not be null
     * @return a list of post tag deleted
     */
    @NonNull
    List<PostTag> deleteByPostId(@NonNull Integer postId);

    /**
     * Deletes post tags by tag id.
     *
     * @param tagId tag id must not be null
     * @return a list of post tag deleted
     */
    @NonNull
    List<PostTag> deleteByTagId(@NonNull Integer tagId);

    /**
     * Finds post count by tag id collection.
     *
     * @param tagIds tag id collection must not be null
     * @return a list of tag post count projection
     */
    @Query("select new run.blog.app.model.projection.TagPostPostCountProjection(count(pt.postId), pt.tagId) from PostTag pt where pt.tagId in ?1 group by pt.tagId")
    @NonNull
    List<TagPostPostCountProjection> findPostCountByTagIds(@NonNull Collection<Integer> tagIds);

    /**
     * Finds post count of tag.
     *
     * @return a list of tag post count projection
     */
    @Query("select new run.blog.app.model.projection.TagPostPostCountProjection(count(pt.postId), pt.tagId) from PostTag pt group by pt.tagId")
    @NonNull
    List<TagPostPostCountProjection> findPostCount();
}
