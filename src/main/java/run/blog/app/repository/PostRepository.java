package run.blog.app.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import run.blog.app.repository.base.BasePostRepository;
import run.blog.app.model.entity.Post;


/**
 * Post repository.
 *
 * @author johnniang
 * @author ryanwang
 */
public interface PostRepository extends BasePostRepository<Post>, JpaSpecificationExecutor<Post> {

    @Override
    @Query("select sum(p.visits) from Post p")
    Long countVisit();

    @Override
    @Query("select sum(p.likes) from Post p")
    Long countLike();

}
