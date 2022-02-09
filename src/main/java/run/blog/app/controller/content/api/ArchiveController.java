package run.blog.app.controller.content.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.blog.app.model.vo.ArchiveMonthVO;
import run.blog.app.model.vo.ArchiveYearVO;
import run.blog.app.service.PostService;

import java.util.List;

/**
 * Archive portal controller.
 *
 * @author johnniang
 * @date 4/2/19
 */
@RestController("ApiContentArchiveController")
@RequestMapping("/api/content/archives")
public class ArchiveController {

    private final PostService postService;

    public ArchiveController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("years")
    public List<ArchiveYearVO> listYearArchives() {
        return postService.listYearArchives();
    }

    @GetMapping("months")
    public List<ArchiveMonthVO> listMonthArchives() {
        return postService.listMonthArchives();
    }
}
