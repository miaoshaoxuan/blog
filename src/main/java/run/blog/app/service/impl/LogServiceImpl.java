package run.blog.app.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import run.blog.app.model.dto.LogDTO;
import run.blog.app.model.entity.Log;
import run.blog.app.repository.LogRepository;
import run.blog.app.service.LogService;
import run.blog.app.service.base.AbstractCrudService;

/**
 * LogService implementation class
 *
 * @author ryanwang
 * @date 2019-03-14
 */
@Service
public class LogServiceImpl extends AbstractCrudService<Log, Long> implements LogService {

    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        super(logRepository);
        this.logRepository = logRepository;
    }

    @Override
    public Page<LogDTO> pageLatest(int top) {
        Assert.isTrue(top > 0, "Top number must not be less than 0");

        // Build page request
        PageRequest latestPageable = PageRequest.of(0, top, Sort.by(Sort.Direction.DESC, "createTime"));

        // List all
        return listAll(latestPageable).map(log -> new LogDTO().convertFrom(log));
    }
}
