package run.blog.app.repository;

import org.springframework.lang.NonNull;
import run.blog.app.model.entity.Menu;
import run.blog.app.repository.base.BaseRepository;

/**
 * Menu repository.
 *
 * @author johnniang
 */
public interface MenuRepository extends BaseRepository<Menu, Integer> {

    boolean existsByName(@NonNull String name);

    boolean existsByIdNotAndName(@NonNull Integer id, @NonNull String name);
}
