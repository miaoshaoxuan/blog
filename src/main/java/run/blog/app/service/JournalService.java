package run.blog.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.blog.app.model.dto.JournalDTO;
import run.blog.app.model.dto.JournalWithCmtCountDTO;
import run.blog.app.model.entity.Journal;
import run.blog.app.model.enums.JournalType;
import run.blog.app.model.params.JournalParam;
import run.blog.app.model.params.JournalQuery;
import run.blog.app.service.base.CrudService;

import java.util.List;

/**
 * Journal service interface.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-24
 */
public interface JournalService extends CrudService<Journal, Integer> {

    /**
     * Creates a journal.
     *
     * @param journalParam journal param must not be null
     * @return created journal
     */
    @NonNull
    Journal createBy(@NonNull JournalParam journalParam);

    /**
     * Updates a journal.
     *
     * @param journal journal must not be null
     * @return updated journal
     */
    Journal updateBy(@NonNull Journal journal);

    /**
     * Gets latest journals.
     *
     * @param top max size
     * @return latest journal page
     */
    Page<Journal> pageLatest(int top);

    /**
     * Pages journals.
     *
     * @param journalQuery journal query must not be null
     * @param pageable     page info must not be null
     * @return a page of journal
     */
    @NonNull
    Page<Journal> pageBy(@NonNull JournalQuery journalQuery, @NonNull Pageable pageable);

    /**
     * Lists by type.
     *
     * @param type     journal type must not be null
     * @param pageable page info must not be null
     * @return a page of journal
     */
    @NonNull
    Page<Journal> pageBy(@NonNull JournalType type, @NonNull Pageable pageable);

    /**
     * Converts to journal dto.
     *
     * @param journal journal must not be null
     * @return journal dto
     */
    @NonNull
    JournalDTO convertTo(@NonNull Journal journal);

    /**
     * Converts to journal with comment count dto list.
     *
     * @param journals journal list
     * @return journal with comment count dto list
     */
    @NonNull
    List<JournalWithCmtCountDTO> convertToCmtCountDto(@Nullable List<Journal> journals);

    /**
     * Converts to journal with comment count dto page.
     *
     * @param journalPage journal page must not be null
     * @return a page of journal with comment count dto
     */
    @NonNull
    Page<JournalWithCmtCountDTO> convertToCmtCountDto(@NonNull Page<Journal> journalPage);
}
