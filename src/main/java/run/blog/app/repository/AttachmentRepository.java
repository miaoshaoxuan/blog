package run.blog.app.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import run.blog.app.model.entity.Attachment;
import run.blog.app.model.enums.AttachmentType;
import run.blog.app.repository.base.BaseRepository;

import java.util.List;

/**
 * Attachment repository
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-03
 */
public interface AttachmentRepository extends BaseRepository<Attachment, Integer>, JpaSpecificationExecutor<Attachment> {

    /**
     * Find all attachment media type.
     *
     * @return list of media type.
     */
    @Query(value = "select distinct a.mediaType from Attachment a")
    List<String> findAllMediaType();

    /**
     * Find all attachment type.
     *
     * @return list of type.
     */
    @Query(value = "select distinct a.type from Attachment a")
    List<AttachmentType> findAllType();

    /**
     * Counts by attachment path.
     *
     * @param path attachment path must not be blank
     * @return count of the given path
     */
    long countByPath(@NonNull String path);
}
