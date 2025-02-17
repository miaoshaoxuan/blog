package run.blog.app.service.impl;

import org.springframework.stereotype.Service;
import run.blog.app.exception.ServiceException;
import run.blog.app.model.dto.StatisticDTO;
import run.blog.app.model.dto.StatisticWithUserDTO;
import run.blog.app.model.dto.UserDTO;
import run.blog.app.model.entity.User;
import run.blog.app.model.enums.CommentStatus;
import run.blog.app.model.enums.PostStatus;
import run.blog.app.service.CategoryService;
import run.blog.app.service.JournalCommentService;
import run.blog.app.service.JournalService;
import run.blog.app.service.LinkService;
import run.blog.app.service.OptionService;
import run.blog.app.service.PostCommentService;
import run.blog.app.service.PostService;
import run.blog.app.service.SheetCommentService;
import run.blog.app.service.SheetService;
import run.blog.app.service.StatisticService;
import run.blog.app.service.TagService;
import run.blog.app.service.UserService;
import run.blog.app.service.*;

/**
 * Statistic service implementation.
 *
 * @author ryanwang
 * @date 2019-12-16
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    private final PostService postService;

    private final SheetService sheetService;

    private final JournalService journalService;

    private final PostCommentService postCommentService;

    private final SheetCommentService sheetCommentService;

    private final JournalCommentService journalCommentService;

    private final OptionService optionService;

    private final LinkService linkService;

    private final CategoryService categoryService;

    private final TagService tagService;

    private final UserService userService;

    public StatisticServiceImpl(PostService postService,
            SheetService sheetService,
            JournalService journalService,
            PostCommentService postCommentService,
            SheetCommentService sheetCommentService,
            JournalCommentService journalCommentService,
            OptionService optionService,
            LinkService linkService,
            CategoryService categoryService,
            TagService tagService,
            UserService userService) {
        this.postService = postService;
        this.sheetService = sheetService;
        this.journalService = journalService;
        this.postCommentService = postCommentService;
        this.sheetCommentService = sheetCommentService;
        this.journalCommentService = journalCommentService;
        this.optionService = optionService;
        this.linkService = linkService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public StatisticDTO getStatistic() {
        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setPostCount(postService.countByStatus(PostStatus.PUBLISHED));

        // Handle comment count
        long postCommentCount = postCommentService.countByStatus(CommentStatus.PUBLISHED);
        long sheetCommentCount = sheetCommentService.countByStatus(CommentStatus.PUBLISHED);
        long journalCommentCount = journalCommentService.countByStatus(CommentStatus.PUBLISHED);

        statisticDTO.setCommentCount(postCommentCount + sheetCommentCount + journalCommentCount);
        statisticDTO.setTagCount(tagService.count());
        statisticDTO.setCategoryCount(categoryService.count());
        statisticDTO.setJournalCount(journalService.count());

        long birthday = optionService.getBirthday();
        long days = (System.currentTimeMillis() - birthday) / (1000 * 24 * 3600);
        statisticDTO.setEstablishDays(days);
        statisticDTO.setBirthday(birthday);

        statisticDTO.setLinkCount(linkService.count());
        statisticDTO.setVisitCount(postService.countVisit() + sheetService.countVisit());
        statisticDTO.setLikeCount(postService.countLike() + sheetService.countLike());
        return statisticDTO;
    }

    @Override
    public StatisticWithUserDTO getStatisticWithUser() {

        StatisticDTO statisticDTO = getStatistic();

        StatisticWithUserDTO statisticWithUserDTO = new StatisticWithUserDTO();
        statisticWithUserDTO.convertFrom(statisticDTO);

        User user = userService.getCurrentUser().orElseThrow(() -> new ServiceException("未查询到博主信息"));
        statisticWithUserDTO.setUser(new UserDTO().convertFrom(user));

        return statisticWithUserDTO;
    }
}
