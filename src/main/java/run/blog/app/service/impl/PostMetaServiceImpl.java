package run.blog.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import run.blog.app.repository.base.BaseMetaRepository;
import run.blog.app.exception.NotFoundException;
import run.blog.app.model.entity.PostMeta;
import run.blog.app.repository.PostRepository;
import run.blog.app.service.PostMetaService;

/**
 * Post meta service implementation class.
 *
 * @author ryanwang
 * @author ikaisec
 * @author guqing
 * @date 2019-08-04
 */
@Slf4j
@Service
public class PostMetaServiceImpl extends BaseMetaServiceImpl<PostMeta> implements PostMetaService {

    private final PostRepository postRepository;

    public PostMetaServiceImpl(BaseMetaRepository<PostMeta> baseMetaRepository, PostRepository postRepository) {
        super(baseMetaRepository);
        this.postRepository = postRepository;
    }

    @Override
    public void validateTarget(@NotNull Integer postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("查询不到该文章的信息").setErrorData(postId));
    }
}
