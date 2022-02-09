package run.blog.app.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import run.blog.app.repository.base.BaseRepository;
import run.blog.app.model.entity.Photo;

import java.util.List;

/**
 * Photo repository.
 *
 * @author johnniang
 */
public interface PhotoRepository extends BaseRepository<Photo, Integer>, JpaSpecificationExecutor<Photo> {

    /**
     * Query photos by team
     *
     * @param team team
     * @param sort sort
     * @return list of photo
     */
    List<Photo> findByTeam(String team, Sort sort);
}
