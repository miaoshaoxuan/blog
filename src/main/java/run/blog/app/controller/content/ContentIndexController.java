package run.blog.app.controller.content;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import run.blog.app.controller.content.model.PostModel;
import run.blog.app.model.entity.Post;
import run.blog.app.model.enums.PostPermalinkType;
import run.blog.app.service.OptionService;
import run.blog.app.service.PostService;

import java.util.Objects;

/**
 * Blog index page controller
 *
 * @author ryanwang
 * @date 2019-03-17
 */
@Slf4j
@Controller
@RequestMapping
public class ContentIndexController {

    private final PostService postService;

    private final OptionService optionService;

    private final PostModel postModel;

    public ContentIndexController(PostService postService,
            OptionService optionService,
            PostModel postModel) {
        this.postService = postService;
        this.optionService = optionService;
        this.postModel = postModel;
    }


    /**
     * Render blog index
     *
     * @param p     post id
     * @param model model
     * @return template path: themes/{theme}/index.ftl
     */
    @GetMapping
    public String index(Integer p, String token, Model model) {

        PostPermalinkType permalinkType = optionService.getPostPermalinkType();

        if (PostPermalinkType.ID.equals(permalinkType) && !Objects.isNull(p)) {
            Post post = postService.getById(p);
            return postModel.content(post, token, model);
        }

        return this.index(model, 1);
    }

    /**
     * Render blog index
     *
     * @param model model
     * @param page  current page number
     * @return template path: themes/{theme}/index.ftl
     */
    @GetMapping(value = "page/{page}")
    public String index(Model model,
            @PathVariable(value = "page") Integer page) {
        return postModel.list(page, model);
    }
}
