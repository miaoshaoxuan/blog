package run.blog.app.handler.theme.config;

import org.springframework.lang.NonNull;
import run.blog.app.handler.theme.config.support.Group;

import java.io.IOException;
import java.util.List;

/**
 * Theme config resolver interface.
 *
 * @author johnniang
 * @date 2019-04-10
 */
public interface ThemeConfigResolver {

    /**
     * Resolves content as group list.
     *
     * @param content content must not be blank
     * @return a list of group
     * @throws IOException throws when content conversion fails
     */
    @NonNull
    List<Group> resolve(@NonNull String content) throws IOException;

}
