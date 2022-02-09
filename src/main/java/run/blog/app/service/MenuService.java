package run.blog.app.service;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import run.blog.app.service.base.CrudService;
import run.blog.app.model.dto.MenuDTO;
import run.blog.app.model.entity.Menu;
import run.blog.app.model.params.MenuParam;
import run.blog.app.model.vo.MenuVO;

import java.util.List;

/**
 * Menu service.
 *
 * @author johnniang
 * @author ryanwang
 */
public interface MenuService extends CrudService<Menu, Integer> {

    /**
     * Lists all menu dtos.
     *
     * @param sort must not be null
     * @return a list of menu output dto
     */
    @NonNull
    List<MenuDTO> listDtos(@NonNull Sort sort);

    /**
     * Creates a menu.
     *
     * @param menuParam must not be null
     * @return created menu
     */
    @NonNull
    Menu createBy(@NonNull MenuParam menuParam);

    /**
     * Lists as menu tree.
     *
     * @param sort sort info must not be null
     * @return a menu tree
     */
    List<MenuVO> listAsTree(Sort sort);
}
