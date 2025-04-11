package stark.magicinsight.domain.entities;

import lombok.Data;

import lombok.EqualsAndHashCode;
import stark.coderaider.fluentschema.commons.NamingConvention;
import stark.coderaider.fluentschema.commons.annotations.Column;
import stark.coderaider.fluentschema.commons.annotations.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(namingConvention = NamingConvention.LOWER_CASE_WITH_UNDERSCORE)
public class UserVideoInfo extends DomainBase
{
    /**
     * Name of the corresponding object in OSS.
     */
    @Column(type = "VARCHAR(255)")
    private String nameInOss;

    @Column(type = "VARCHAR(255)")
    private String markedNameInOss;

    /**
     * Title of the video.
     */
    @Column(type = "VARCHAR(255)")
    private String title;

    /**
     * URL of the cover of the video.
     */
    @Column(type = "VARCHAR(255)")
    private String coverUrl;

    /**
     * Introduction to the video.
     */
    @Column(type = "VARCHAR(1000)")
    private String introduction;

    /**
     * Path of the summary file of the video.
     */
    @Column(type = "VARCHAR(255)")
    private String summaryFileName;
}
