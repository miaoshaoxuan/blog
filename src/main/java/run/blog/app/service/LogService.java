package run.blog.app.service;

import org.springframework.data.domain.Page;
import run.blog.app.service.base.CrudService;
import run.blog.app.model.dto.LogDTO;
import run.blog.app.model.entity.Log;

/**
 * Log service.
 *
 * @author johnniang
 */
public interface LogService extends CrudService<Log, Long> {

    /**
     * Lists latest logs.
     *
     * @param top top number must not be less than 0
     * @return a page of latest logs
     */
    Page<LogDTO> pageLatest(int top);
}
