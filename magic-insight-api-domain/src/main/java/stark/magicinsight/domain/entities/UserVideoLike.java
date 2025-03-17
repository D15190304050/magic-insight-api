package stark.magicinsight.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import stark.coderaider.fluentschema.commons.annotations.NotMapped;

/**
 * Records of likes & dislikes of videos.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NotMapped
public class UserVideoLike extends DomainBase
{
    /**
     * ID of the user who gave the opinion, either like or dislike.
     */
    long userId;

    /**
     * ID of the video associated with the opinion.
     */
    long videoId;

    /**
     * Like type: 1 - Like; 2 - Dislike.
     */
    int likeType;
}
