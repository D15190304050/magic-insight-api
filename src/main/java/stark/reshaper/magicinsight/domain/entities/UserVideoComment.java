package stark.reshaper.magicinsight.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import stark.coderaider.fluentschema.commons.NamingConvention;
import stark.coderaider.fluentschema.commons.annotations.Column;
import stark.coderaider.fluentschema.commons.annotations.Table;

/**
 * Comments of videos.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(namingConvention = NamingConvention.LOWER_CASE_WITH_UNDERSCORE)
public class UserVideoComment extends DomainBase
{
    /**
     * ID of the user who comments.
     */
    private long userId;

    /**
     * ID of the video that the comment is associated with.
     */
    private long videoId;

    /**
     * Content of the comment.
     */
    @Column(type = "VARCHAR(200)")
    private String content;

    /**
     * ID of the parent comment. -1 if no parent.
     */
    private long parentId;
}
