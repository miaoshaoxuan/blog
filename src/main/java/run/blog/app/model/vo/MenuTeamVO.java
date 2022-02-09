package run.blog.app.model.vo;

import lombok.Data;
import lombok.ToString;
import run.blog.app.model.dto.MenuDTO;

import java.util.List;

/**
 * Menu team vo.
 *
 * @author ryanwang
 * @date 2019/8/28
 */
@Data
@ToString
public class MenuTeamVO {

    private String team;

    private List<MenuDTO> menus;
}
