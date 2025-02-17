package run.blog.app.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import run.blog.app.model.entity.Option;
import run.blog.app.repository.base.BaseRepository;

import java.util.Optional;

/**
 * Option repository.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-20
 */
public interface OptionRepository extends BaseRepository<Option, Integer>, JpaSpecificationExecutor<Option> {

    /**
     * Query option by key
     *
     * @param key key
     * @return Option
     */
    Optional<Option> findByKey(String key);

    /**
     * Delete option by key
     *
     * @param key key
     */
    void deleteByKey(String key);
}
