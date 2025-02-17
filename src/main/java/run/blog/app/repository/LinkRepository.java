package run.blog.app.repository;

import org.springframework.data.jpa.repository.Query;
import run.blog.app.model.entity.Link;
import run.blog.app.repository.base.BaseRepository;

import java.util.List;

/**
 * Link repository.
 *
 * @author johnniang
 */
public interface LinkRepository extends BaseRepository<Link, Integer> {

    /**
     * Find all link teams.
     *
     * @return a list of teams
     */
    @Query(value = "select distinct a.team from Link a")
    List<String> findAllTeams();

    boolean existsByNameAndIdNot(String name, Integer id);

    boolean existsByUrlAndIdNot(String url, Integer id);
}
