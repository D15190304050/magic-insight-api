package stark.magicinsight.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import stark.coderaider.fluentschema.commons.annotations.NotMapped;

/**
 * Play records of videos.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NotMapped
public class VideoPlayRecord extends DomainBase
{
    /**
     * ID of the user who watched the video.
     */
    private long userId;

    /**
     * ID of the video that is watched.
     */
    private long videoId;
}
