package run.blog.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import run.blog.app.model.dto.post.BasePostDetailDTO;
import run.blog.app.model.dto.post.BasePostMinimalDTO;
import run.blog.app.model.dto.post.BasePostSimpleDTO;
import run.blog.app.repository.base.BasePostRepository;
import run.blog.app.exception.AlreadyExistsException;
import run.blog.app.exception.BadRequestException;
import run.blog.app.exception.NotFoundException;
import run.blog.app.exception.ServiceException;
import run.blog.app.model.entity.BasePost;
import run.blog.app.model.enums.PostEditorType;
import run.blog.app.model.enums.PostStatus;
import run.blog.app.model.properties.PostProperties;
import run.blog.app.service.OptionService;
import run.blog.app.service.base.AbstractCrudService;
import run.blog.app.service.base.BasePostService;
import run.blog.app.utils.DateUtils;
import run.blog.app.utils.HaloUtils;
import run.blog.app.utils.MarkdownUtils;
import run.blog.app.utils.ServiceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Base post service implementation.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-24
 */
@Slf4j
public abstract class BasePostServiceImpl<POST extends BasePost> extends AbstractCrudService<POST, Integer> implements BasePostService<POST> {

    private final BasePostRepository<POST> basePostRepository;

    private final OptionService optionService;

    private final Pattern summaryPattern = Pattern.compile("\t|\r|\n");

    public BasePostServiceImpl(BasePostRepository<POST> basePostRepository,
            OptionService optionService) {
        super(basePostRepository);
        this.basePostRepository = basePostRepository;
        this.optionService = optionService;
    }

    @Override
    public long countVisit() {
        return Optional.ofNullable(basePostRepository.countVisit()).orElse(0L);
    }

    @Override
    public long countLike() {
        return Optional.ofNullable(basePostRepository.countLike()).orElse(0L);
    }

    @Override
    public long countByStatus(PostStatus status) {
        Assert.notNull(status, "Post status must not be null");

        return basePostRepository.countByStatus(status);
    }

    @Override
    public POST getBySlug(String slug) {
        Assert.hasText(slug, "Slug must not be blank");

        return basePostRepository.getBySlug(slug).orElseThrow(() -> new NotFoundException("查询不到该文章的信息").setErrorData(slug));
    }

    @Override
    public POST getBy(PostStatus status, String slug) {
        Assert.notNull(status, "Post status must not be null");
        Assert.hasText(slug, "Post slug must not be blank");

        Optional<POST> postOptional = basePostRepository.getBySlugAndStatus(slug, status);

        return postOptional.orElseThrow(() -> new NotFoundException("查询不到该文章的信息").setErrorData(slug));
    }

    @Override
    public POST getBy(PostStatus status, Integer id) {
        Assert.notNull(status, "Post status must not be null");
        Assert.notNull(id, "Post id must not be null");

        Optional<POST> postOptional = basePostRepository.getByIdAndStatus(id, status);

        return postOptional.orElseThrow(() -> new NotFoundException("查询不到该文章的信息").setErrorData(id));
    }

    @Override
    public List<POST> listAllBy(PostStatus status) {
        Assert.notNull(status, "Post status must not be null");

        return basePostRepository.findAllByStatus(status);
    }


    @Override
    public List<POST> listPrevPosts(POST post, int size) {
        Assert.notNull(post, "Post must not be null");

        String indexSort = optionService.getByPropertyOfNonNull(PostProperties.INDEX_SORT).toString();

        PageRequest pageRequest = PageRequest.of(0, size, Sort.by(ASC, indexSort));

        switch (indexSort) {
            case "createTime":
                return basePostRepository.findAllByStatusAndCreateTimeAfter(PostStatus.PUBLISHED, post.getCreateTime(), pageRequest).getContent();
            case "editTime":
                return basePostRepository.findAllByStatusAndEditTimeAfter(PostStatus.PUBLISHED, post.getEditTime(), pageRequest).getContent();
            case "visits":
                return basePostRepository.findAllByStatusAndVisitsAfter(PostStatus.PUBLISHED, post.getVisits(), pageRequest).getContent();
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public List<POST> listNextPosts(POST post, int size) {
        Assert.notNull(post, "Post must not be null");

        String indexSort = optionService.getByPropertyOfNonNull(PostProperties.INDEX_SORT).toString();

        PageRequest pageRequest = PageRequest.of(0, size, Sort.by(DESC, indexSort));

        switch (indexSort) {
            case "createTime":
                return basePostRepository.findAllByStatusAndCreateTimeBefore(PostStatus.PUBLISHED, post.getCreateTime(), pageRequest).getContent();
            case "editTime":
                return basePostRepository.findAllByStatusAndEditTimeBefore(PostStatus.PUBLISHED, post.getEditTime(), pageRequest).getContent();
            case "visits":
                return basePostRepository.findAllByStatusAndVisitsBefore(PostStatus.PUBLISHED, post.getVisits(), pageRequest).getContent();
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public Optional<POST> getPrevPost(POST post) {
        List<POST> posts = listPrevPosts(post, 1);

        return CollectionUtils.isEmpty(posts) ? Optional.empty() : Optional.of(posts.get(0));
    }

    @Override
    public Optional<POST> getNextPost(POST post) {
        List<POST> posts = listNextPosts(post, 1);

        return CollectionUtils.isEmpty(posts) ? Optional.empty() : Optional.of(posts.get(0));
    }

    @Override
    public Page<POST> pageLatest(int top) {
        Assert.isTrue(top > 0, "Top number must not be less than 0");

        PageRequest latestPageable = PageRequest.of(0, top, Sort.by(DESC, "createTime"));

        return listAll(latestPageable);
    }

    /**
     * Lists latest posts.
     *
     * @param top top number must not be less than 0
     * @return latest posts
     */
    @Override
    public List<POST> listLatest(int top) {
        Assert.isTrue(top > 0, "Top number must not be less than 0");

        PageRequest latestPageable = PageRequest.of(0, top, Sort.by(DESC, "createTime"));
        return basePostRepository.findAllByStatus(PostStatus.PUBLISHED, latestPageable).getContent();
    }

    @Override
    public Page<POST> pageBy(Pageable pageable) {
        Assert.notNull(pageable, "Page info must not be null");

        return listAll(pageable);
    }


    @Override
    public Page<POST> pageBy(PostStatus status, Pageable pageable) {
        Assert.notNull(status, "Post status must not be null");
        Assert.notNull(pageable, "Page info must not be null");

        return basePostRepository.findAllByStatus(status, pageable);
    }

    @Override
    @Transactional
    public void increaseVisit(long visits, Integer postId) {
        Assert.isTrue(visits > 0, "Visits to increase must not be less than 1");
        Assert.notNull(postId, "Post id must not be null");

        boolean finishedIncrease;
        if (basePostRepository.getByIdAndStatus(postId, PostStatus.DRAFT).isPresent())
        {
            finishedIncrease = true;
            log.info("Post with id: [{}] is a draft and visits will not be updated", postId);
        } else {
            finishedIncrease = basePostRepository.updateVisit(visits, postId) == 1;
        }

        if (!finishedIncrease) {
            log.error("Post with id: [{}] may not be found", postId);
            throw new BadRequestException("Failed to increase visits " + visits + " for post with id " + postId);
        }
    }

    @Override
    @Transactional
    public void increaseLike(long likes, Integer postId) {
        Assert.isTrue(likes > 0, "Likes to increase must not be less than 1");
        Assert.notNull(postId, "Goods id must not be null");

        long affectedRows = basePostRepository.updateLikes(likes, postId);

        if (affectedRows != 1) {
            log.error("Post with id: [{}] may not be found", postId);
            throw new BadRequestException("Failed to increase likes " + likes + " for post with id " + postId);
        }
    }

    @Override
    @Transactional
    public void increaseVisit(Integer postId) {
        increaseVisit(1L, postId);
    }

    @Override
    @Transactional
    public void increaseLike(Integer postId) {
        increaseLike(1L, postId);
    }

    @Override
    @Transactional
    public POST createOrUpdateBy(POST post) {
        Assert.notNull(post, "Post must not be null");

        String originalContent = post.getOriginalContent();
        originalContent = HaloUtils.cleanHtmlTag(originalContent);

        post.setWordCount((long) originalContent.length());

        // Render content
        if (post.getEditorType().equals(PostEditorType.MARKDOWN)) {
            post.setFormatContent(MarkdownUtils.renderHtml(post.getOriginalContent()));
        } else {
            post.setFormatContent(post.getOriginalContent());
        }

        // if password is not empty,change status to intimate
        if (StringUtils.isNotEmpty(post.getPassword()) && post.getStatus() != PostStatus.DRAFT) {
            post.setStatus(PostStatus.INTIMATE);
        }

        // Create or update post
        if (ServiceUtils.isEmptyId(post.getId())) {
            // The sheet will be created
            return create(post);
        }

        // The sheet will be updated
        // Set edit time
        post.setEditTime(DateUtils.now());

        // Update it
        return update(post);
    }

    @Override
    public POST filterIfEncrypt(POST post) {
        Assert.notNull(post, "Post must not be null");

        if (StringUtils.isNotBlank(post.getPassword())) {
            String tip = "The post is encrypted by author";
            post.setSummary(tip);
            post.setOriginalContent(tip);
            post.setFormatContent(tip);
        }

        return post;
    }

    @Override
    public BasePostMinimalDTO convertToMinimal(POST post) {
        Assert.notNull(post, "Post must not be null");

        return new BasePostMinimalDTO().convertFrom(post);
    }

    @Override
    public List<BasePostMinimalDTO> convertToMinimal(List<POST> posts) {
        if (CollectionUtils.isEmpty(posts)) {
            return Collections.emptyList();
        }

        return posts.stream()
                .map(this::convertToMinimal)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BasePostMinimalDTO> convertToMinimal(Page<POST> postPage) {
        Assert.notNull(postPage, "Post page must not be null");

        return postPage.map(this::convertToMinimal);
    }

    @Override
    public BasePostSimpleDTO convertToSimple(POST post) {
        Assert.notNull(post, "Post must not be null");

        BasePostSimpleDTO basePostSimpleDTO = new BasePostSimpleDTO().convertFrom(post);

        // Set summary
        if (StringUtils.isBlank(basePostSimpleDTO.getSummary())) {
            basePostSimpleDTO.setSummary(generateSummary(post.getFormatContent()));
        }

        return basePostSimpleDTO;
    }

    @Override
    public List<BasePostSimpleDTO> convertToSimple(List<POST> posts) {
        if (CollectionUtils.isEmpty(posts)) {
            return Collections.emptyList();
        }

        return posts.stream()
                .map(this::convertToSimple)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BasePostSimpleDTO> convertToSimple(Page<POST> postPage) {
        Assert.notNull(postPage, "Post page must not be null");

        return postPage.map(this::convertToSimple);
    }

    @Override
    public BasePostDetailDTO convertToDetail(POST post) {
        Assert.notNull(post, "Post must not be null");

        return new BasePostDetailDTO().convertFrom(post);
    }

    @Override
    @Transactional
    public POST updateDraftContent(String content, Integer postId) {
        Assert.isTrue(!ServiceUtils.isEmptyId(postId), "Post id must not be empty");

        if (content == null) {
            content = "";
        }

        POST post = getById(postId);

        if (!StringUtils.equals(content, post.getOriginalContent())) {
            // If content is different with database, then update database
            int updatedRows = basePostRepository.updateOriginalContent(content, postId);
            if (updatedRows != 1) {
                throw new ServiceException("Failed to update original content of post with id " + postId);
            }
            // Set the content
            post.setOriginalContent(content);
        }

        return post;
    }

    @Override
    @Transactional
    public POST updateStatus(PostStatus status, Integer postId) {
        Assert.notNull(status, "Post status must not be null");
        Assert.isTrue(!ServiceUtils.isEmptyId(postId), "Post id must not be empty");

        // Get post
        POST post = getById(postId);

        if (!status.equals(post.getStatus())) {
            // Update post
            int updatedRows = basePostRepository.updateStatus(status, postId);
            if (updatedRows != 1) {
                throw new ServiceException("Failed to update post status of post with id " + postId);
            }

            post.setStatus(status);
        }

        // Sync content
        if (PostStatus.PUBLISHED.equals(status)) {
            // If publish this post, then convert the formatted content
            String formatContent = MarkdownUtils.renderHtml(post.getOriginalContent());
            int updatedRows = basePostRepository.updateFormatContent(formatContent, postId);

            if (updatedRows != 1) {
                throw new ServiceException("Failed to update post format content of post with id " + postId);
            }

            post.setFormatContent(formatContent);
        }

        return post;
    }

    @Override
    @Transactional
    public List<POST> updateStatusByIds(List<Integer> ids, PostStatus status) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return ids.stream().map(id -> {
            return updateStatus(status, id);
        }).collect(Collectors.toList());
    }

    @Override
    public List<BasePostDetailDTO> replaceUrl(String oldUrl, String newUrl) {
        List<POST> posts = listAll();
        List<POST> replaced = new ArrayList<>();
        posts.forEach(post -> {
            if (StringUtils.isNotEmpty(post.getThumbnail())) {
                post.setThumbnail(post.getThumbnail().replaceAll(oldUrl, newUrl));
            }
            if (StringUtils.isNotEmpty(post.getOriginalContent())) {
                post.setOriginalContent(post.getOriginalContent().replaceAll(oldUrl, newUrl));
            }
            if (StringUtils.isNotEmpty(post.getFormatContent())) {
                post.setFormatContent(post.getFormatContent().replaceAll(oldUrl, newUrl));
            }
            replaced.add(post);
        });
        List<POST> updated = updateInBatch(replaced);
        return updated.stream().map(this::convertToDetail).collect(Collectors.toList());
    }

    @Override
    public String generateDescription(String content) {
        Assert.notNull(content, "html content must not be null");

        String text = HaloUtils.cleanHtmlTag(content);

        Matcher matcher = summaryPattern.matcher(text);
        text = matcher.replaceAll("");

        // Get summary length
        Integer summaryLength = optionService.getByPropertyOrDefault(PostProperties.SUMMARY_LENGTH, Integer.class, 150);

        return StringUtils.substring(text, 0, summaryLength);
    }

    @Override
    public POST create(POST post) {
        // Check title
        slugMustNotExist(post);

        return super.create(post);
    }

    @Override
    public POST update(POST post) {
        // Check title
        slugMustNotExist(post);

        return super.update(post);
    }

    /**
     * Check if the slug is exist.
     *
     * @param post post must not be null
     */
    protected void slugMustNotExist(@NonNull POST post) {
        Assert.notNull(post, "Post must not be null");

        // Get slug count
        boolean exist;

        if (ServiceUtils.isEmptyId(post.getId())) {
            // The sheet will be created
            exist = basePostRepository.existsBySlug(post.getSlug());
        } else {
            // The sheet will be updated
            exist = basePostRepository.existsByIdNotAndSlug(post.getId(), post.getSlug());
        }

        if (exist) {
            throw new AlreadyExistsException("文章别名 " + post.getSlug() + " 已存在");
        }
    }

    @NonNull
    protected String generateSummary(@NonNull String htmlContent) {
        Assert.notNull(htmlContent, "html content must not be null");

        String text = HaloUtils.cleanHtmlTag(htmlContent);

        Matcher matcher = summaryPattern.matcher(text);
        text = matcher.replaceAll("");

        // Get summary length
        Integer summaryLength = optionService.getByPropertyOrDefault(PostProperties.SUMMARY_LENGTH, Integer.class, 150);

        return StringUtils.substring(text, 0, summaryLength);
    }
}
