package run.blog.app.core.freemarker.tag;

import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import run.blog.app.model.properties.PrimaryProperties;
import run.blog.app.model.support.HaloConst;
import run.blog.app.service.MenuService;
import run.blog.app.service.OptionService;

import java.io.IOException;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Freemarker custom tag of menu.
 *
 * @author ryanwang
 * @date 2019-03-22
 */
@Component
public class MenuTagDirective implements TemplateDirectiveModel {

    private static final String METHOD_KEY = "method";

    private final MenuService menuService;

    private final OptionService optionService;

    public MenuTagDirective(Configuration configuration, MenuService menuService, OptionService optionService) {
        this.menuService = menuService;
        this.optionService = optionService;
        configuration.setSharedVariable("menuTag", this);
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        final DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

        if (params.containsKey(HaloConst.METHOD_KEY)) {
            String method = params.get(HaloConst.METHOD_KEY).toString();
            switch (method) {
                case "list":
                    String listTeam = optionService.getByPropertyOrDefault(PrimaryProperties.DEFAULT_MENU_TEAM, String.class, "");
                    env.setVariable("menus", builder.build().wrap(menuService.listByTeam(listTeam, Sort.by(DESC, "priority"))));
                    break;
                case "tree":
                    String treeTeam = optionService.getByPropertyOrDefault(PrimaryProperties.DEFAULT_MENU_TEAM, String.class, "");
                    env.setVariable("menus", builder.build().wrap(menuService.listByTeamAsTree(treeTeam, Sort.by(DESC, "priority"))));
                    break;
                case "listTeams":
                    env.setVariable("teams", builder.build().wrap(menuService.listTeamVos(Sort.by(DESC, "priority"))));
                    break;
                case "listByTeam":
                    String team = params.get("team").toString();
                    env.setVariable("menus", builder.build().wrap(menuService.listByTeam(team, Sort.by(DESC, "priority"))));
                    break;
                case "treeByTeam":
                    String treeTeamParam = params.get("team").toString();
                    env.setVariable("menus", builder.build().wrap(menuService.listByTeamAsTree(treeTeamParam, Sort.by(DESC, "priority"))));
                    break;
                case "count":
                    env.setVariable("count", builder.build().wrap(menuService.count()));
                    break;
                default:
                    break;
            }
        }
        body.render(env.getOut());
    }
}
