package run.blog.app.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import run.blog.app.model.enums.JournalType;

import javax.persistence.*;

/**
 * Journal entity
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-22
 */
@Data
@Entity
@Table(name = "journals")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Journal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "run.blog.app.model.entity.support.CustomIdGenerator")
    private Integer id;

    @Column(name = "source_content", nullable = false)
    @Lob
    private String sourceContent;

    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @Column(name = "likes")
    @ColumnDefault("0")
    private Long likes;

    @Column(name = "type")
    @ColumnDefault("1")
    private JournalType type;

    @Override
    public void prePersist() {
        super.prePersist();

        if (likes == null || likes < 0) {
            likes = 0L;
        }

        if (type == null) {
            type = JournalType.PUBLIC;
        }
    }
}
