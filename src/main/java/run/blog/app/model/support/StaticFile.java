package run.blog.app.model.support;

import lombok.Data;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

/**
 * Static file.
 *
 * @author ryanwang
 * @date 2019-12-06
 */
@Data
@ToString
public class StaticFile implements Comparator<StaticFile> {

    private String id;

    private String name;

    private String path;

    private String relativePath;

    private Boolean isFile;

    private String mimeType;

    private Long createTime;

    private List<StaticFile> children;

    @Override
    public int compare(StaticFile leftFile, StaticFile rightFile) {
        if (leftFile.isFile && !rightFile.isFile) {
            return 1;
        }

        if (!leftFile.isFile && rightFile.isFile) {
            return -1;
        }

        return leftFile.getName().compareTo(rightFile.getName());
    }
}
